package io.github.bbang208.windowinsets

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import io.github.bbang208.windowinsets.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnApplyWindowInsetsListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root, this)

        ViewCompat.setWindowInsetsAnimationCallback(
            binding.root,
            object : WindowInsetsAnimationCompat.Callback(
                DISPATCH_MODE_STOP
            ) {
                override fun onProgress(
                    insets: WindowInsetsCompat,
                    runningAnimations: MutableList<WindowInsetsAnimationCompat>
                ): WindowInsetsCompat {
                    return insets
                }

                override fun onEnd(animation: WindowInsetsAnimationCompat) {
                    super.onEnd(animation)
                    val isKeyboardVisible = WindowInsetsCompat.toWindowInsetsCompat(binding.root.rootWindowInsets).isVisible(WindowInsetsCompat.Type.ime())
                    Log.e("MainActivity", "keyboardVisible: $isKeyboardVisible")

                    //ViewCompat.getRootWindowInsets(binding.root)?.isVisible(WindowInsetsCompat.Type.ime())
                }
            })
    }

    override fun onApplyWindowInsets(view: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        //시스템바, InputMethod
        val types = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()

        // 패딩으로 설정하여, 결정된 insets을 적용
        val typeInsets = insets.getInsets(types)
        view.setPadding(typeInsets.left, typeInsets.top, typeInsets.right, typeInsets.bottom)

        // 새로운 WindowInsetsCompat.CONSUMED를 반환하여 insets가 뷰 계층 구조로 더 이상 전달되지 않도록 함
        // 이것은 deprecated된 WindowInsetsCompat.consumeSystemWindowInsets() 및 관련 함수를 대체합니다
        return WindowInsetsCompat.CONSUMED
    }
}