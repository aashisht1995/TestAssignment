package sg.whyq.testassignment.ui

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import sg.whyq.testassignment.utills.ConnectionDetector

class MainActivityTest {

    private var mainActivity: MainActivity? = null


    @BeforeEach
    fun setUp() {
        mainActivity = MainActivity()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    public fun isInternetActive() {
        val result = ConnectionDetector.isConnectingToInternet(mainActivity)
        if (result)
            assert(true)
        else
            fail {"No Internet"}
    }
}