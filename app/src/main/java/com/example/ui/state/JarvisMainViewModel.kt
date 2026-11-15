package com.example.ui.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class OrbActivityState(val label: String, val statusText: String) {
    IDLE("Standby", "Neural Core Standby • Phase 1 UI Ready"),
    LISTENING("Listening", "Audio Waveform Preview Active"),
    THINKING("Thinking", "Reasoning Pipeline Standby (Gemini Phase 3)"),
    SPEAKING("Speaking", "Voice Synthesis Preview Active")
}

enum class JarvisLanguage(val displayName: String, val nativeName: String, val code: String) {
    ENGLISH("English", "English", "en"),
    HINDI("Hindi", "हिन्दी", "hi"),
    MARATHI("Marathi", "मराठी", "mr"),
    KOREAN("Korean", "한국어", "ko"),
    JAPANESE("Japanese", "日本語", "ja")
}

enum class AuthMode(val title: String) {
    SIGN_IN("Sign In"),
    SIGN_UP("Create Account"),
    FORGOT_PASSWORD("Reset Password")
}

data class AuthFormState(
    val mode: AuthMode = AuthMode.SIGN_IN,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val showPassword: Boolean = false,
    val validationError: String? = null,
    val infoBanner: String? = null,
    val isPreviewSessionActive: Boolean = false
)

data class JarvisUiState(
    val isDarkTheme: Boolean = true,
    val selectedLanguage: JarvisLanguage = JarvisLanguage.ENGLISH,
    val orbState: OrbActivityState = OrbActivityState.IDLE,
    val quickCommandDraft: String = "",
    val aiChatDraft: String = "",
    val searchQueryDraft: String = "",
    val auth: AuthFormState = AuthFormState(),
    val showNotificationsSheet: Boolean = false,
    val showModuleDirectorySheet: Boolean = false,
    val memoryEnabled: Boolean = false,
    val privateModeEnabled: Boolean = true,
    val speechSpeed: Float = 1.0f,
    val selectedVoiceProfile: String = "Aurora Neural (EN/HI)"
)

class JarvisMainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(JarvisUiState())
    val uiState: StateFlow<JarvisUiState> = _uiState.asStateFlow()

    fun toggleTheme() {
        _uiState.update { it.copy(isDarkTheme = !it.isDarkTheme) }
    }

    fun setDarkTheme(dark: Boolean) {
        _uiState.update { it.copy(isDarkTheme = dark) }
    }

    fun selectLanguage(language: JarvisLanguage) {
        _uiState.update { it.copy(selectedLanguage = language) }
    }

    fun setOrbState(state: OrbActivityState) {
        _uiState.update { it.copy(orbState = state) }
    }

    fun updateQuickCommandDraft(value: String) {
        _uiState.update { it.copy(quickCommandDraft = value) }
    }

    fun updateAiChatDraft(value: String) {
        _uiState.update { it.copy(aiChatDraft = value) }
    }

    fun updateSearchQueryDraft(value: String) {
        _uiState.update { it.copy(searchQueryDraft = value) }
    }

    fun toggleNotificationsSheet(open: Boolean) {
        _uiState.update { it.copy(showNotificationsSheet = open) }
    }

    fun toggleModuleDirectorySheet(open: Boolean) {
        _uiState.update { it.copy(showModuleDirectorySheet = open) }
    }

    fun setMemoryEnabled(enabled: Boolean) {
        _uiState.update { it.copy(memoryEnabled = enabled) }
    }

    fun setPrivateModeEnabled(enabled: Boolean) {
        _uiState.update { it.copy(privateModeEnabled = enabled) }
    }

    fun setSpeechSpeed(speed: Float) {
        _uiState.update { it.copy(speechSpeed = speed) }
    }

    fun setSelectedVoiceProfile(profile: String) {
        _uiState.update { it.copy(selectedVoiceProfile = profile) }
    }

    // Auth Form Handlers (Strict client validation; real backend Auth scheduled in Phase 2)
    fun setAuthMode(mode: AuthMode) {
        _uiState.update {
            it.copy(
                auth = it.auth.copy(
                    mode = mode,
                    validationError = null,
                    infoBanner = null
                )
            )
        }
    }

    fun updateAuthFullName(value: String) {
        _uiState.update {
            it.copy(auth = it.auth.copy(fullName = value, validationError = null))
        }
    }

    fun updateAuthEmail(value: String) {
        _uiState.update {
            it.copy(auth = it.auth.copy(email = value, validationError = null))
        }
    }

    fun updateAuthPassword(value: String) {
        _uiState.update {
            it.copy(auth = it.auth.copy(password = value, validationError = null))
        }
    }

    fun updateAuthConfirmPassword(value: String) {
        _uiState.update {
            it.copy(auth = it.auth.copy(confirmPassword = value, validationError = null))
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update {
            it.copy(auth = it.auth.copy(showPassword = !it.auth.showPassword))
        }
    }

    fun submitAuthForm() {
        val current = _uiState.value.auth
        val trimmedEmail = current.email.trim()
        val emailValid = trimmedEmail.contains("@") && trimmedEmail.contains(".") && trimmedEmail.length >= 5

        val error = when (current.mode) {
            AuthMode.SIGN_IN -> when {
                !emailValid -> "Enter a valid email address."
                current.password.length < 8 -> "Password must be at least 8 characters."
                else -> null
            }
            AuthMode.SIGN_UP -> when {
                current.fullName.trim().length < 2 -> "Enter your full name."
                !emailValid -> "Enter a valid email address."
                current.password.length < 8 -> "Password must be at least 8 characters."
                current.password != current.confirmPassword -> "Passwords do not match."
                else -> null
            }
            AuthMode.FORGOT_PASSWORD -> when {
                !emailValid -> "Enter a valid email address for recovery."
                else -> null
            }
        }

        if (error != null) {
            _uiState.update {
                it.copy(auth = it.auth.copy(validationError = error, infoBanner = null))
            }
        } else {
            val message = when (current.mode) {
                AuthMode.SIGN_IN -> "Validation passed. Real cloud authentication & session persistence will be connected in Phase 2 (no simulated login)."
                AuthMode.SIGN_UP -> "Account form validated. Cloud registration & database provisioning will be connected in Phase 2."
                AuthMode.FORGOT_PASSWORD -> "Email validated. Password recovery dispatch will be enabled once Phase 2 backend auth is connected."
            }
            _uiState.update {
                it.copy(auth = it.auth.copy(validationError = null, infoBanner = message))
            }
        }
    }

    fun enterWorkspacePreview() {
        _uiState.update {
            it.copy(auth = it.auth.copy(isPreviewSessionActive = true, validationError = null))
        }
    }
}
