package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.navigation.JarvisDestination
import com.example.ui.state.AuthMode
import com.example.ui.state.JarvisLanguage
import com.example.ui.state.JarvisMainViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read app name string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("JARVIS AI", appName)
    }

    @Test
    fun `all 17 main navigation destinations are registered`() {
        assertEquals(17, JarvisDestination.mainDestinations.size)
    }

    @Test
    fun `viewModel theme language and auth validation behave accurately`() {
        val vm = JarvisMainViewModel()
        assertEquals(true, vm.uiState.value.isDarkTheme)
        vm.toggleTheme()
        assertEquals(false, vm.uiState.value.isDarkTheme)

        vm.selectLanguage(JarvisLanguage.HINDI)
        assertEquals(JarvisLanguage.HINDI, vm.uiState.value.selectedLanguage)

        vm.setAuthMode(AuthMode.SIGN_IN)
        vm.updateAuthEmail("invalid")
        vm.submitAuthForm()
        assertNotNull(vm.uiState.value.auth.validationError)

        vm.updateAuthEmail("commander@jarvis.ai")
        vm.updateAuthPassword("securepass123")
        vm.submitAuthForm()
        assertNull(vm.uiState.value.auth.validationError)
        assertNotNull(vm.uiState.value.auth.infoBanner)
    }
}
