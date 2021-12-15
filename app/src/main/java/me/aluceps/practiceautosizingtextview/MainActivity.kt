package me.aluceps.practiceautosizingtextview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import me.aluceps.practiceautosizingtextview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val currentValue = MutableLiveData<Float>()
    private var originalSize1 = 0
    private var originalSize2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.textView1.viewTreeObserver.addOnGlobalLayoutListener {
            if (originalSize1 == 0) {
                originalSize1 = binding.textView1.width
            }
        }
        binding.container.viewTreeObserver.addOnGlobalLayoutListener {
            if (originalSize2 == 0) {
                originalSize2 = binding.container.width
            }
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
            binding.textView1.layoutParams.width = (originalSize1 * it).toInt()
            binding.textView1.requestLayout()
            binding.container.layoutParams.width = (originalSize2 * it).toInt()
            binding.container.requestLayout()
        }
    }
}
