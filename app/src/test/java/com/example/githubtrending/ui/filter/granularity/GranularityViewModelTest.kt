package com.example.githubtrending.ui.filter.granularity

import com.example.domain.model.Granularity
import com.example.test.BaseTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GranularityViewModelTest : BaseTest() {


    private lateinit var viewModel: GranularityViewModel

    @Before
    fun init() {
        viewModel = GranularityViewModel(dispatcherProvider)
    }

    @Test
    fun `get granularities`() {
        testCoroutineRule.runBlockingTest {

            val granularity = Granularity.DAILY
            viewModel.getGranularities(granularity)

            val state = viewModel.state.value!!
            Assert.assertEquals(state.granularities.size, Granularity.values().size)

            val item = state.granularities.first { it.item == granularity }
            Assert.assertTrue(item.isSelected)
        }
    }
}