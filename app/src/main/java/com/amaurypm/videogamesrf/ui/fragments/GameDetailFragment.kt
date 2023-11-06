package com.amaurypm.videogamesrf.ui.fragments

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.amaurypm.videogamesrf.R
import com.amaurypm.videogamesrf.application.VideoGamesRFApp
import com.amaurypm.videogamesrf.data.GameRepository
import com.amaurypm.videogamesrf.data.remote.model.GameDetailDto
import com.amaurypm.videogamesrf.databinding.FragmentGameDetailBinding
import com.amaurypm.videogamesrf.util.Constants
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val GAME_ID = "game_id"


class GameDetailFragment : Fragment() {

    private var gameId: String? = null

    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: GameRepository
    private lateinit var mp: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            gameId = args.getString(GAME_ID)

            Log.d(Constants.LOGTAG, "Id recibido: $gameId")

            repository = (requireActivity().application as VideoGamesRFApp).repository

            lifecycleScope.launch {

                gameId?.let { id ->
                    //val call: Call<GameDetailDto> = repository.getGameDetail(id)
                    val call: Call<GameDetailDto> = repository.getGameDetailApiary(id)

                    call.enqueue(object: Callback<GameDetailDto>{
                        override fun onResponse(
                            call: Call<GameDetailDto>,
                            response: Response<GameDetailDto>
                        ) {
                            val videoName = response.body()?.Video ?: "" // Asumo que response.body()?.Video es la variable que contiene el nombre del video

                            val videoPath1 = "android.resource://${requireContext().packageName}/raw/$videoName"
                            Log.d("video","android.resource01://${requireContext().packageName}/raw/$videoName")

                            val videoPath = "android.resource://${requireContext().packageName}/${R.raw.elprincipito}"
                            Log.d("video","android.resource02://${requireContext().packageName}/${R.raw.elprincipito}")


                            binding.apply {
                                pbLoading.visibility = View.GONE
                                tvTitle.text = response.body()?.titulo
                                tvLongDesc.text = response.body()?.long_desc
                                vvImage.setVideoPath(videoPath1)

                                val mc = MediaController(requireContext())
                                mc.setAnchorView(vvImage)
                                vvImage.setMediaController(mc)
                                vvImage.start()
                            }



                        }

                        override fun onFailure(call: Call<GameDetailDto>, t: Throwable) {
                            binding.pbLoading.visibility = View.GONE

                            Toast.makeText(requireActivity(), "No hay conexión", Toast.LENGTH_SHORT).show()
                        }

                    })
                }

            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(gameId: String) =
            GameDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(GAME_ID, gameId)
                }
            }
    }
}