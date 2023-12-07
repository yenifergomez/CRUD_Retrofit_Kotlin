package com.xcheko51x.crud_retrofit_kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CommentAdapter(
    var context: Context,
    var listaComment: ArrayList<Comment>
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private var onClick: OnItemClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_comment, parent, false)
        return CommentViewHolder(vista)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = listaComment[position]

        holder.tvIdComment.text = comment.id.toString()
        holder.tvTitulo.text = comment.name
        holder.tvComment.text = comment.comment

        holder.btnEditar.setOnClickListener {
            onClick?.editarComment(comment)
        }

        holder.btnBorrar.setOnClickListener {
            onClick?.borrarComment(comment)
        }
    }

    override fun getItemCount(): Int {
        return listaComment.size
    }

    inner class CommentViewHolder(itemView: View) : ViewHolder(itemView) {
        val tvIdComment = itemView.findViewById(R.id.tvIdComment) as TextView
        val tvTitulo = itemView.findViewById(R.id.tvTitulo) as TextView
        val tvComment = itemView.findViewById(R.id.tvComment) as TextView

        val btnEditar = itemView.findViewById(R.id.btnEditar) as Button
        val btnBorrar = itemView.findViewById(R.id.btnBorrar) as Button
    }

    interface OnItemClicked {
        fun editarComment(comment: Comment)
        fun borrarComment(comment: Comment)
    }


    fun setOnClick(onClick: OnItemClicked) {
        this.onClick = onClick
    }
}
