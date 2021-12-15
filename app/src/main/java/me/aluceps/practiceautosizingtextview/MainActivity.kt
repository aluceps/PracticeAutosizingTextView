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
    private var screenSize = 0
    private var originalSize1 = 0
    private var originalSize2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        observe()
    }

    private fun setupView() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            if (screenSize == 0) {
                screenSize = binding.root.width
            }
        }
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
        binding.slider.addOnChangeListener { _, value, _ ->
            currentValue.postValue((value + 0.1f) * 2f)
        }
    }

    private fun observe() {
        currentValue.observe(this) {
            if (it == null) return@observe
            (originalSize1 * it).toInt().let {
                if (screenSize >= it) {
                    binding.textView1.layoutParams.width = it
                    binding.textView1.requestLayout()
                }
            }
            (originalSize2 * it).toInt().let {
                if (screenSize >= it) {
                    binding.container.layoutParams.width = it
                    binding.container.requestLayout()
                }
            }
        }
    }
}
