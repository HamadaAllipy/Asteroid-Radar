package com.example.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.domain.Asteroid
import com.example.asteroidradar.domain.ImageOfDay
import com.example.asteroidradar.main.MainAdapter
import com.squareup.picasso.Picasso




@BindingAdapter("asteroidDetailImage")
fun bindAsteroidDetailImage(imageView: ImageView, isDanger: Boolean) {
    if (!isDanger) {
        imageView.setImageResource(R.drawable.asteroid_safe)
    } else {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    }
}

@BindingAdapter("astronomyData")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("visibilityProgress")
fun visibilityProgress(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("asteroidIcon")
fun bindAsteroidStatusIcon(imageView: ImageView, isHazardous: Boolean) {

    if(!isHazardous){
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
    else {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    }

}


@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}


@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, image: ImageOfDay?) {
    if (image != null)
        Picasso.get().load(image.url).into(imageView)
}

@BindingAdapter("allData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
    val adapter = recyclerView.adapter as MainAdapter
    adapter.submitList(data)
}