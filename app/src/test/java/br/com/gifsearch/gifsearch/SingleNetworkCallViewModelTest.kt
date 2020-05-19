package br.com.gifsearch.gifsearch

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.gifsearch.android.app.data.model.GifResponseDTO
import br.com.gifsearch.android.app.data.source.remote.repository.GifsRetrofitRepository
import br.com.gifsearch.android.app.ui.gifs.trendings.TrendingGifsViewModel
import br.com.gifsearch.gifsearch.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SingleNetworkCallViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiHelper: Context

    @Mock
    private lateinit var gifsRetrofitRepository: GifsRetrofitRepository

    @Mock
    private lateinit var observer: Observer<GifResponseDTO>

    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<GifResponseDTO>())
                .`when`(apiHelper)
            val viewModel = TrendingGifsViewModel(apiHelper, gifsRetrofitRepository)
            viewModel.gifs.observeForever(observer)
            viewModel.gifs.removeObserver(observer)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
            val viewModel = TrendingGifsViewModel(apiHelper, gifsRetrofitRepository)
            viewModel.gifs.observeForever(observer)
            viewModel.gifs.removeObserver(observer)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }
}