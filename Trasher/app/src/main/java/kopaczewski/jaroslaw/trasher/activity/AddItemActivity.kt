package kopaczewski.jaroslaw.trasher.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.cardview.widget.CardView
import kopaczewski.jaroslaw.trasher.R
import kopaczewski.jaroslaw.trasher.activity.api.DataLoader
import kopaczewski.jaroslaw.trasher.activity.api.DataLoader.addItem
import kopaczewski.jaroslaw.trasher.activity.api.DataLoader.currentItems
import kopaczewski.jaroslaw.trasher.activity.data.Item
import kopaczewski.jaroslaw.trasher.activity.data.ItemSend
import kopaczewski.jaroslaw.trasher.activity.ui.map.MapFragment
import kopaczewski.jaroslaw.trasher.databinding.ActivityAddItemBinding
import kotlin.concurrent.thread

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding
    private lateinit var nameInput: EditText
    private lateinit var categoryInput: EditText
    private lateinit var addButton: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nameInput = binding.nameInput
        categoryInput = binding.categoryInput
        addButton = binding.addButton
        val latitude = intent.getDoubleExtra("latitude", 40.1234)
        val longitude = intent.getDoubleExtra("longitude", 45.5678)

        addButton.setOnClickListener {
            val item = ItemSend(
                name = nameInput.text.toString(),
                latitude = latitude.toFloat(),
                longitude = longitude.toFloat(),
                user = 11,
                status = false,
                category = categoryInput.text.toString(),
                likes = 0,
                views = 0,
            )
            thread {
                addItem(item)
            }.join()
            val fragment = getFragmentManager().findFragmentById(R.id.mapFragment) as MapFragment
            fragment.reloadMap()
            finish()
        }

    }

}