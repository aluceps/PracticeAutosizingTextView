package me.aluceps.practiceautosizingtextview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import me.aluceps.practiceautosizingtextview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val currentValue = MutableLiveData<Float>()
    private var originalSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.textView1.viewTreeObserver.addOnGlobalLayoutListener {
            setOriginalSize(binding.textView1.width)
        }
        setupView()
        observe()
    }

    private fun setupView() {
        binding.slider.addOnChangeListener { _, value, _ ->
            currentValue.postValue(value * 10)
        }
    }

    private fun observe() {
        currentValue.observe(this) {
            if (it == null) return@observe
            Log.d("###", "originalSize: $originalSize currentSize: $it sum: ${originalSize * it}")
            binding.textView1.layoutParams.width = (originalSize * it).toInt()
            binding.textView1.requestLayout()
        }
    }

    private fun setOriginalSize(value: Int) {
        if (originalSize == 0) {
            originalSize = value
        }
    }
}
