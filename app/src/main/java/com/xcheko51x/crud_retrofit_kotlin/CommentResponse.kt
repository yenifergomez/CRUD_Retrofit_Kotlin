package com.xcheko51x.crud_retrofit_kotlin

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("listaComment") var listaComment: ArrayList<Comment>
)
