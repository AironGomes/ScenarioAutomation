package com.airongomes.scenarioautomation.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.airongomes.scenarioautomation.R
import com.airongomes.scenarioautomation.databinding.FragmentAboutBinding
import com.airongomes.scenarioautomation.utils.AboutInfo

class AboutFragment : Fragment() {

    // Referência para a animação
    private var currentAnimator: Animator? = null

    // Duração da animação
    private var shortAnimationDuration: Int = 0

    lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_about,
                container,
                false)

        // Cria uma instancia de AboutInfo data class e associa com o layout
        val aboutInfo = AboutInfo()
        binding.aboutInfo = aboutInfo

        // ClickListener para imageAbout
        binding.imageAbout.setOnClickListener {
            zoomImageAnimation(binding.imageAbout)
        }

        // Retêm tempo de curta duração para animação do sistema e salva em cache
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        return binding.root
    }

    /**
     * Esta função é responsável por criar uma animação para expandir visualização da imagem.
     * Para implementação dessas caracteristicas foram seguidas as orientações da documentação da Google Developers em:
     * 'https://developer.android.com/training/animation/zoom.html'.
     */
    private fun zoomImageAnimation(view: View) {
        // Cancela a animação se estiver em execução
        currentAnimator?.cancel()

        // Calcula as posições inicial e final
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        // Define as posições de view inicial e final
        view.getGlobalVisibleRect(startBoundsInt)
        binding.viewGroup
                .getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        // Ajusta fatores de escala para manter proporção
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            // Estender limites horizontais
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Estender limites verticais
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Torna imageAboutFullScreen visivel e esconde as views restantes
        binding.linearlayout.visibility = View.GONE
        binding.imageAboutFullScreen.visibility = View.VISIBLE

        // Define posição dinâmica de escala_x e escala_y
        binding.imageAboutFullScreen.pivotX = 0f
        binding.imageAboutFullScreen.pivotY = 0f

        // Constroi e executa animação
        currentAnimator = AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(
                    binding.imageAboutFullScreen,
                    View.X,
                    startBounds.left,
                    finalBounds.left)
            ).apply {
                with(ObjectAnimator.ofFloat(binding.imageAboutFullScreen, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(binding.imageAboutFullScreen, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(binding.imageAboutFullScreen, View.SCALE_Y, startScale, 1f))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
            start()
        }

        // ClickListener para imageAboutFullScreen
        binding.imageAboutFullScreen.setOnClickListener {
            currentAnimator?.cancel()

            // Define animação para retornar a imagem original
            currentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(binding.imageAboutFullScreen, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(binding.imageAboutFullScreen, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(binding.imageAboutFullScreen, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(binding.imageAboutFullScreen, View.SCALE_Y, startScale))
                }
                duration = shortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        binding.linearlayout.visibility = View.VISIBLE
                        binding.imageAboutFullScreen.visibility = View.GONE
                        currentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        binding.linearlayout.visibility = View.VISIBLE
                        binding.imageAboutFullScreen.visibility = View.GONE
                        currentAnimator = null
                    }
                })
                start()
            }
        }
    }

}
