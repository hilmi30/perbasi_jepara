package com.perusdajepara.perbasijepara.signup

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.login.LoginActivity
import com.perusdajepara.perbasijepara.utils.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_signup.*
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import org.jetbrains.anko.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.perusdajepara.perbasijepara.activity.MainActivity


class SignupActivity : AppCompatActivity(), SignupView, com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {

    private lateinit var presenter: SignupPresenter
    private var jenisKelamin: Int? = null
    private var tanggalLahir: String? = null

    private lateinit var namaLengkap: String
    private lateinit var oldPass: String
    private lateinit var pass: String
    private lateinit var passRepeat: String
    private lateinit var email: String
    private lateinit var alamat: String
    private lateinit var update: String
    private var thumbnail: Bitmap? = null
    private var imgUri: Uri? = null

    private val maxImageSize = 500f
    private val galleryCode = 1
    private val cameraWriteCode  = 2
    private val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        onAttachView()
    }

    override fun onAttachView() {
        presenter = SignupPresenter()
        presenter.onAttach(this)
        presenter.initFirebase()

        update = intent.getStringExtra(getString(R.string.update_profile))

        checkStatus()
        setCheckBoxGender()
        registerBtn()
        registerWithFacebook()
        setTanggalBtn()
        ambilGambar()
    }

    private fun checkStatus() {

        when (update) {
            "1" -> {
                pass_lama_edt.visible()
                pass_edt.hint = getString(R.string.pass_baru)
                email_edt.gone()
                register_btn.text = getString(R.string.update)

                presenter.setDataProfile()
            }
            else -> {
                pass_lama_edt.gone()
                pass_edt.hint = getString(R.string.password)
                email_edt.visible()
                register_btn.text = getString(R.string.register)
                imgUri = Uri.parse("android.resource://$packageName/drawable/ic_launcher_background")
                jenisKelamin = 1
            }
        }
    }

    private fun ambilGambar() {
        take_photo_btn.setOnClickListener {
            alert {
                title = "Ambil Gambar"
                message = "Pilih aksi untuk mengambil gambar"
                positiveButton("Galeri") {
                    dariGaleri()
                }
                negativeButton("Kamera") {
                    dariKamera()
                }
            }.show()
        }
    }

    private fun dariGaleri() {
        val handler = object : PermissionHandler() {
            override fun onGranted() {
                // buka galeri

                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), galleryCode)
            }

            override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                alertGallery()
            }
        }

        // check gallery permission
        Permissions.check(this, Manifest.permission.READ_EXTERNAL_STORAGE, null, handler)
    }

    private fun dariKamera() {

        val handler = object : PermissionHandler() {
            override fun onGranted() {
                // buka kamera
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, cameraWriteCode)
            }

            override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                alertKamera()
            }
        }

        // check camera permission
        Permissions.check(this, permission, null, null, handler)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == cameraWriteCode) {
            if (data?.extras != null) {
                thumbnail = (data.extras as Bundle).get("data") as Bitmap
                val bitmap = scaleDown(thumbnail as Bitmap, maxImageSize, false)
                photo_profile_img.imageBitmap = bitmap

                // simpan gambar
                toast("File gambar tersimpan di ${saveImage(thumbnail, this)}")
            }
        }
        else if (requestCode == galleryCode) {
            if (data != null) {
                val contentURI = data.data
                try
                {
                    thumbnail = MediaStore.Images.Media.getBitmap(contentResolver, contentURI)
                    val bitmap = scaleDown(thumbnail as Bitmap, maxImageSize, false)
                    photo_profile_img.setImageBitmap(bitmap)
                }
                catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun alertGallery() {
        alert {
            title = "Izin Akses"
            message = "Dibutuhkan izin akses penyimpanan untuk bisa mengambil gambar"
            positiveButton("Izinkan") {
                dariGaleri()
            }
            negativeButton("Tutup") {
                it.dismiss()
            }
        }.show()
    }

    private fun alertKamera() {
        alert {
            title = "Izin Akses"
            message = "Dibutuhkan izin akses kamera dan penyimpanan untuk bisa mengambil gambar"
            positiveButton("Izinkan") {
                dariKamera()
            }
            negativeButton("Tutup") {
                it.dismiss()
            }
        }.show()
    }

    private fun setTanggalBtn() {
        btn_set_tanggal.setOnClickListener {
            SpinnerDatePickerDialogBuilder()
                    .context(this)
                    .callback(this)
                    .showTitle(true)
                    .showDaySpinner(true)
                    .defaultDate(
                            Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                    .build()
                    .show()
        }
    }

    override fun onDateSet(view: com.tsongkha.spinnerdatepicker.DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        tanggalLahir = "$dayOfMonth/${monthOfYear+1}/$year"
        tv_tanggal.text = tanggalLahir
    }

    private fun setCheckBoxGender() {
        gender.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_male -> jenisKelamin = 1
                R.id.radio_female -> jenisKelamin = 0
            }
        }
    }

    private fun registerWithFacebook() {
        register_with_fb_btn.setOnClickListener {
            doRegisterWithFB()
        }
    }

    private fun doRegisterWithFB() {

    }

    private fun registerBtn() {
        register_btn.setOnClickListener {
            doRegister()
        }
    }

    private fun doRegister() {
        namaLengkap = nama_lengkap_edt.text.toString().trim()
        email = email_edt.text.toString().trim()
        pass = pass_edt.text.toString()
        passRepeat = ulangi_pass_edt.text.toString()
        alamat = alamat_edt.text.toString()
        oldPass = pass_lama_edt.text.toString()

        if (thumbnail != null) {
            val baos = ByteArrayOutputStream()
            thumbnail?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val path = MediaStore.Images.Media.insertImage(contentResolver, thumbnail, "Title", null)
            imgUri = Uri.parse(path)
        }

        when (update) {
            "1" -> if (validation()) presenter.updateProfile(namaLengkap, jenisKelamin, tanggalLahir, alamat, imgUri as Uri, pass, oldPass)
            else ->  if (validation()) presenter.register(namaLengkap, email, pass, jenisKelamin, tanggalLahir, alamat, imgUri as Uri)
        }
    }

    private fun validation(): Boolean {

        var valid = true

        if (namaLengkap.isEmpty()) {
            nama_lengkap_edt.error = getString(R.string.nama_tidak_benar)
            valid = false
        }

        if (update == "0") {
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                email_edt.error = getString(R.string.emai_tidak_benar)
                valid = false
            }
            if (pass.isEmpty()) {
                pass_edt.error = getString(R.string.pass_tidak_benar)
                valid = false
            }
            if (pass != passRepeat) {
                ulangi_pass_edt.error = getString(R.string.pass_tidak_sama)
                valid = false
            }
            if (pass.length < 6) {
                pass_edt.error = getString(R.string.pass_lebih_dari)
                valid = false
            }
        }

        if (update == "1") {
            if (pass.isNotEmpty() || passRepeat.isNotEmpty()) {
                if (pass != passRepeat) {
                    ulangi_pass_edt.error = getString(R.string.pass_tidak_sama)
                    valid = false
                }
                if (pass.length < 6) {
                    pass_edt.error = getString(R.string.pass_lebih_dari)
                    valid = false
                }
            }
        }

        if (tanggalLahir.isNullOrEmpty()) {
            toast(getString(R.string.tgl_tidak_boleh_kosong))
            valid = false
        }
        if (alamat.isEmpty()) {
            alamat_edt.error = getString(R.string.alamat_tidak_benar)
            valid = false
        }

        return valid
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun errorNama() {
        nama_lengkap_edt.error = "nama tidak benar"
    }

    override fun errorEmail() {
        email_edt.error = "email tidak benar"
    }

    override fun errorPass() {
        pass_edt.error = "password tidak benar"
        ulangi_pass_edt.error = "password tidak benar"
    }

    override fun errorPassTidakSama() {
        pass_edt.error = "password tidak sama"
        ulangi_pass_edt.error = "password tidak sama"
    }

    override fun errorPassKurang() {
        pass_edt.error = "password harus lebih dari 6 karakter"
    }

    override fun failure() {
        toast("Terjadi kesalahan")
    }

    override fun successRegister() {
        startActivity<LoginActivity>()
        finish()
    }

    override fun showLoading() {
        register_progress.visible()
    }

    override fun hideLoading() {
        register_progress.gone()
    }

    override fun successUpdate() {
        finish()
    }

    override fun gotToMain() {
        startActivity<MainActivity>()
        finish()
    }

    override fun setDataProfile(user: FirebaseUser?) {
        val alamat = intent.getStringExtra(getString(R.string.alamat))
        val gender = intent.getStringExtra(getString(R.string.gender))
        tanggalLahir = intent.getStringExtra(getString(R.string.tanggalLahir))
        imgUri = user?.photoUrl

        nama_lengkap_edt.setText(user?.displayName)
        alamat_edt.setText(alamat)
        tv_tanggal.text = tanggalLahir
        Picasso.get().load(imgUri).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background).into(photo_profile_img)

        when (gender) {
            "1" -> radio_male.isChecked = true
            else -> radio_female.isChecked = true
        }
    }

    // simpan gambar ke storage
    private fun saveImage(myBitmap: Bitmap?, c: Context):String {
        val imageDirectory = "/AgusAppPhoto"
        val bytes = ByteArrayOutputStream()
        myBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val wallpaperDirectory = File(
                (Environment.getExternalStorageDirectory()).toString() + imageDirectory
        )
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists()) wallpaperDirectory.mkdirs()

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                    .timeInMillis).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(c,
                    arrayOf(f.path),
                    arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.absolutePath)

            return f.absolutePath
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    // resize image
    private fun scaleDown(realImage: Bitmap, maxImageSize: Float, filter: Boolean): Bitmap {
        val ratio = Math.min(
                maxImageSize / realImage.width,
                maxImageSize / realImage.height
        )
        val width = Math.round(ratio * realImage.width)
        val height = Math.round(ratio * realImage.height)

        return Bitmap.createScaledBitmap(
                realImage, width,
                height, filter
        )
    }
}
