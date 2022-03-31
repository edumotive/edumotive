package com.djinc.edumotive.utils

import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup

class modelProps(val obj: Any) {
    val id get() = if (this.obj is ContentfulModel) obj.id else (this.obj as ContentfulModelGroup).id
    val title get() = if (this.obj is ContentfulModel) obj.title else (this.obj as ContentfulModelGroup).title
    val image get() = if (this.obj is ContentfulModel) obj.image else (this.obj as ContentfulModelGroup).image
    val description get() = if (this.obj is ContentfulModel) obj.id else (this.obj as ContentfulModelGroup).description
}
