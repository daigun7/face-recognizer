package com.sgsoft.facerecognizer.ui.tab

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import com.sgsoft.facerecognizer.R
import com.sgsoft.facerecognizer.api.CFRModel
import com.sgsoft.facerecognizer.common.fragment.BaseFragment
import com.sgsoft.facerecognizer.extension.createTempFile
import com.sgsoft.facerecognizer.extension.getRealPath
import com.sgsoft.facerecognizer.ui.result.ResultFragment
import kotlinx.android.synthetic.main.fragment_face.*
import java.io.File
import java.io.IOException
import javax.inject.Inject

class FaceTabFragment : BaseFragment<FaceTabContract.View, FaceTabContract.Presenter>(), FaceTabContract.View {
    @Inject
    override lateinit var mPresenter: FaceTabContract.Presenter

    private var mImageFilePath: String? = null

    companion object {
        const val REQUEST_TAKE_PHOTO = 1
        const val REQUEST_GALLERY = 2
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_face, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        camera.setOnClickListener {
            dispatchTakePictureIntent()
        }

        album.setOnClickListener {
            dispatchPictureIntent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val context = activity?.applicationContext ?: return

        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_TAKE_PHOTO -> {
                    mImageFilePath?.also {
                        mPresenter.recognizeFace(context, File(it))
                    }
                }

                REQUEST_GALLERY -> {
                    data?.data?.getRealPath(context.contentResolver)?.also {
                        mPresenter.recognizeFace(context, File(it))
                    }
                }
            }
        }
    }

    override fun onFaceRecognized(model: CFRModel, file: File) {
        val arguments = Bundle()
        arguments.putParcelable(ResultFragment.ARGUMENT_MODEL, model)
        arguments.putString(ResultFragment.ARGUMENT_FILE_PATH, file.absolutePath)

        val fragment = ResultFragment()
        fragment.arguments = arguments

        fragmentManager?.also {
            fragment.show(it, ResultFragment::class.java.name)
        }
    }

    private fun dispatchPictureIntent() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    private fun dispatchTakePictureIntent() {
        val context = activity?.applicationContext ?: return

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(context.packageManager)?.also {
                val imageFile = try {
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.createTempFile()

                } catch (e: IOException) {
                    null
                }

                imageFile?.also { file ->
                    mImageFilePath = file.absolutePath

                    FileProvider.getUriForFile(context, context.packageName, file).also {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, it)
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO)
                    }
                }
            }
        }
    }
}