package com.example.githubtrending.ui

import com.example.domain.model.Granularity
import com.example.githubtrending.ui.model.Filter
import com.example.githubtrending.ui.model.Order
import com.example.test.BaseTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : BaseTest() {

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
    }

    @Test
    fun `on spoken language selected`() {
        val name = "a"
        val code = "b"

        mainViewModel.onSpokenLanguageSelected(name, code)

        val filter = mainViewModel.filter.value!!

        Assert.assertEquals(filter.spokenLanguage, name)
        Assert.assertEquals(filter.params.spokenLanguageCode, code)
        Assert.assertEquals(filter.state, Filter.State.FILTER)
    }

    @Test
    fun `on programming language selected`() {
        val name = "a"
        val code = "b"

        mainViewModel.onProgrammingLanguageSelected(name, code)

        val filter = mainViewModel.filter.value!!

        Assert.assertEquals(filter.programmingLanguage, name)
        Assert.assertEquals(filter.params.programmingLanguageCode, code)
        Assert.assertEquals(filter.state, Filter.State.FILTER)
    }

    @Test
    fun `on granularity selected`() {
        val granularity = Granularity.MONTHLY

        mainViewModel.onGranularityLanguageSelected(granularity)

        val filter = mainViewModel.filter.value!!

        Assert.assertEquals(filter.params.granularity, granularity)
        Assert.assertEquals(filter.state, Filter.State.FILTER)
    }

    @Test
    fun `on order selected`() {
        val order = Order.AUTHOR

        mainViewModel.order(order.itemId)

        val filter = mainViewModel.filter.value!!

        Assert.assertEquals(filter.order, order)
        Assert.assertEquals(filter.state, Filter.State.SORT)
    }

    @Test
    fun `on reset selected`() {

        mainViewModel.onGranularityLanguageSelected(Granularity.MONTHLY)
        mainViewModel.order(Order.AUTHOR.itemId)

        val name = "a"
        val code = "b"
        mainViewModel.onProgrammingLanguageSelected(name, code)
        mainViewModel.onSpokenLanguageSelected(name, code)

        val filterBeforeReset = mainViewModel.filter.value!!
        Assert.assertEquals(filterBeforeReset.params.granularity, Granularity.MONTHLY)
        Assert.assertEquals(filterBeforeReset.order, Order.AUTHOR)
        Assert.assertEquals(filterBeforeReset.programmingLanguage, name)
        Assert.assertEquals(filterBeforeReset.spokenLanguage, name)

        mainViewModel.reset()

        val filterAfterReset = mainViewModel.filter.value!!

        Assert.assertEquals(filterAfterReset, Filter())
    }
}