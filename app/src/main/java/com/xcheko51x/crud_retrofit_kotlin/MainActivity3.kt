package com.xcheko51x.crud_retrofit_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcheko51x.crud_retrofit_kotlin.databinding.ActivityMain3Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity3 : AppCompatActivity(), CommentAdapter.OnItemClicked {

    lateinit var binding: ActivityMain3Binding
    lateinit var adaptador: CommentAdapter
    var listaComment = arrayListOf<Comment>()
    var comment = Comment(-1, "", "")
    var isEditando = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvComment.layoutManager = LinearLayoutManager(this)
        setupRecyclerView()

        obtenerComment()

        binding.btnAddUpdate.setOnClickListener {
            var isValido = validarCampos()
            if (isValido) {
                if (!isEditando) {
                    agregarComment()
                } else {
                    actualizarComment()
                }
            }
        }
    }

    fun validarCampos(): Boolean {
        return !(binding.etTitulo.text.isNullOrEmpty() || binding.etComment.text.isNullOrEmpty())
    }

    fun obtenerComment() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = WebService.RetrofitClient.webService.obtenerComments()
            runOnUiThread {
                if (call.isSuccessful) {
                    listaComment.clear()
                    listaComment.addAll(call.body() ?: emptyList())
                    adaptador.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@MainActivity3,
                        "Error al consultar todos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun agregarComment() {
        this.comment.name = binding.etTitulo.text.toString()
        this.comment.comment = binding.etComment.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val call = WebService.RetrofitClient.webService.agregarComment(comment)
            runOnUiThread {
                if (call.isSuccessful) {
                    Toast.makeText(this@MainActivity3, call.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                    obtenerComment()
                    limpiarCampos()
                    limpiarObjetos()
                } else {
                    Toast.makeText(this@MainActivity3, call.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun actualizarComment() {
        this.comment.name = binding.etTitulo.text.toString()
        this.comment.comment = binding.etComment.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val call = WebService.RetrofitClient.webService.actualizarComment(comment.id, comment)
            runOnUiThread {
                if (call.isSuccessful) {
                    Toast.makeText(this@MainActivity3, call.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                    obtenerComment()
                    limpiarCampos()
                    limpiarObjetos()

                    binding.btnAddUpdate.setText("agregarcomment")
                    binding.btnAddUpdate.backgroundTintList
                } else {
                    Toast.makeText(this@MainActivity3, call.body().toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    fun limpiarCampos() {
        binding.etTitulo.setText("")
        binding.etComment.setText("")
    }

    fun limpiarObjetos() {
        this.comment.id = -1
        this.comment.name = ""
        this.comment.comment = ""
    }

    fun setupRecyclerView() {
        adaptador = CommentAdapter(this, listaComment)
        adaptador.setOnClick(this@MainActivity3)
        binding.rvComment.adapter = adaptador
    }

    override fun editarComment(comment: Comment) {
        binding.etTitulo.setText(comment.name)
        binding.etComment.setText(comment.comment)
        binding.btnAddUpdate.setText("actualizar usuario")
        binding.btnAddUpdate.backgroundTintList = resources.getColorStateList(R.color.black)
        this.comment = comment
        isEditando = true
    }

    override fun borrarComment(comment: Comment) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = WebService.RetrofitClient.webService.borrarComment(comment.id)
            runOnUiThread {
                if (call.isSuccessful) {
                    Toast.makeText(
                        this@MainActivity3,
                        "Comentario eliminado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@MainActivity3,
                        "Error al eliminar el comentario",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}





