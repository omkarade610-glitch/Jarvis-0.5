package com.example.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigation.JarvisDestination
import com.example.ui.components.JarvisAdaptiveShell
import com.example.ui.screens.AiAssistantScreen
import com.example.ui.screens.AnimeSeriesCreatorScreen
import com.example.ui.screens.AnimeStudioScreen
import com.example.ui.screens.ApkBuilderScreen
import com.example.ui.screens.AppBuilderScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.CgiStudioScreen
import com.example.ui.screens.CodingStudioScreen
import com.example.ui.screens.DocumentAssistantScreen
import com.example.ui.screens.HomeDashboardScreen
import com.example.ui.screens.ImageStudioScreen
import com.example.ui.screens.KnowledgeCenterScreen
import com.example.ui.screens.LiveSearchScreen
import com.example.ui.screens.OwnerDashboardScreen
import com.example.ui.screens.PermissionCenterScreen
import com.example.ui.screens.ProjectManagerScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.VideoStudioScreen
import com.example.ui.screens.VoiceAssistantScreen
import com.example.ui.screens.WebsiteBuilderScreen
import com.example.ui.state.JarvisMainViewModel
import com.example.ui.theme.JarvisAiTheme

@Composable
fun JarvisAiApp(
    viewModel: JarvisMainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = JarvisDestination.fromRoute(backStackEntry?.destination?.route)

    val navigateTo: (JarvisDestination) -> Unit = { dest ->
        if (dest.route != currentDestination.route) {
            navController.navigate(dest.route) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    JarvisAiTheme(darkTheme = uiState.isDarkTheme) {
        JarvisAdaptiveShell(
            currentDestination = currentDestination,
            uiState = uiState,
            onNavigate = navigateTo,
            onToggleTheme = { viewModel.toggleTheme() },
            onOpenNotifications = { viewModel.toggleNotificationsSheet(it) },
            onOpenModuleDirectory = { viewModel.toggleModuleDirectorySheet(it) }
        ) { innerPadding, _ ->
            NavHost(
                navController = navController,
                startDestination = JarvisDestination.HOME.route
            ) {
                composable(JarvisDestination.HOME.route) {
                    HomeDashboardScreen(
                        contentPadding = innerPadding,
                        uiState = uiState,
                        onOrbStateChange = viewModel::setOrbState,
                        onQuickCommandChange = viewModel::updateQuickCommandDraft,
                        onNavigate = navigateTo,
                        onOpenNotifications = { viewModel.toggleNotificationsSheet(true) },
                        onOpenModuleDirectory = { viewModel.toggleModuleDirectorySheet(true) }
                    )
                }
                composable(JarvisDestination.AI_ASSISTANT.route) {
                    AiAssistantScreen(
                        contentPadding = innerPadding,
                        uiState = uiState,
                        onLanguageSelected = viewModel::selectLanguage,
                        onDraftChange = viewModel::updateAiChatDraft,
                        onOpenVoiceAssistant = { navigateTo(JarvisDestination.VOICE_ASSISTANT) }
                    )
                }
                composable(JarvisDestination.VOICE_ASSISTANT.route) {
                    VoiceAssistantScreen(
                        contentPadding = innerPadding,
                        uiState = uiState,
                        onOrbStateChange = viewModel::setOrbState,
                        onSpeechSpeedChange = viewModel::setSpeechSpeed,
                        onVoiceProfileChange = viewModel::setSelectedVoiceProfile,
                        onOpenPermissions = { navigateTo(JarvisDestination.PERMISSION_CENTER) }
                    )
                }
                composable(JarvisDestination.LIVE_SEARCH.route) {
                    LiveSearchScreen(
                        contentPadding = innerPadding,
                        uiState = uiState,
                        onQueryChange = viewModel::updateSearchQueryDraft
                    )
                }
                composable(JarvisDestination.KNOWLEDGE_CENTER.route) {
                    KnowledgeCenterScreen(
                        contentPadding = innerPadding,
                        uiState = uiState,
                        onMemoryToggle = viewModel::setMemoryEnabled,
                        onPrivateModeToggle = viewModel::setPrivateModeEnabled
                    )
                }
                composable(JarvisDestination.CODING_STUDIO.route) {
                    CodingStudioScreen(contentPadding = innerPadding)
                }
                composable(JarvisDestination.WEBSITE_BUILDER.route) {
                    WebsiteBuilderScreen(contentPadding = innerPadding)
                }
                composable(JarvisDestination.APP_BUILDER.route) {
                    AppBuilderScreen(contentPadding = innerPadding)
                }
                composable(JarvisDestination.APK_BUILDER.route) {
                    ApkBuilderScreen(
                        contentPadding = innerPadding,
                        onOpenPermissions = { navigateTo(JarvisDestination.PERMISSION_CENTER) }
                    )
                }
                composable(JarvisDestination.IMAGE_STUDIO.route) {
                    ImageStudioScreen(contentPadding = innerPadding)
                }
                composable(JarvisDestination.ANIME_STUDIO.route) {
                    AnimeStudioScreen(
                        contentPadding = innerPadding,
                        onOpenSeriesCreator = { navigateTo(JarvisDestination.ANIME_SERIES_CREATOR) }
                    )
                }
                composable(JarvisDestination.ANIME_SERIES_CREATOR.route) {
                    AnimeSeriesCreatorScreen(contentPadding = innerPadding)
                }
                composable(JarvisDestination.VIDEO_STUDIO.route) {
                    VideoStudioScreen(
                        contentPadding = innerPadding,
                        onOpenSeriesCreator = { navigateTo(JarvisDestination.ANIME_SERIES_CREATOR) }
                    )
                }
                composable(JarvisDestination.CGI_STUDIO.route) {
                    CgiStudioScreen(contentPadding = innerPadding)
                }
                composable(JarvisDestination.DOCUMENT_ASSISTANT.route) {
                    DocumentAssistantScreen(contentPadding = innerPadding)
                }
                composable(JarvisDestination.PROJECT_MANAGER.route) {
                    ProjectManagerScreen(contentPadding = innerPadding)
                }
                composable(JarvisDestination.OWNER_DASHBOARD.route) {
                    OwnerDashboardScreen(
                        contentPadding = innerPadding,
                        onOpenPermissions = { navigateTo(JarvisDestination.PERMISSION_CENTER) }
                    )
                }
                composable(JarvisDestination.SETTINGS.route) {
                    SettingsScreen(
                        contentPadding = innerPadding,
                        uiState = uiState,
                        onToggleTheme = viewModel::setDarkTheme,
                        onSelectLanguage = viewModel::selectLanguage,
                        onOpenAuth = { navigateTo(JarvisDestination.AUTH) },
                        onOpenPermissions = { navigateTo(JarvisDestination.PERMISSION_CENTER) }
                    )
                }
                composable(JarvisDestination.AUTH.route) {
                    AuthScreen(
                        contentPadding = innerPadding,
                        authState = uiState.auth,
                        onModeChange = viewModel::setAuthMode,
                        onFullNameChange = viewModel::updateAuthFullName,
                        onEmailChange = viewModel::updateAuthEmail,
                        onPasswordChange = viewModel::updateAuthPassword,
                        onConfirmPasswordChange = viewModel::updateAuthConfirmPassword,
                        onTogglePasswordVisibility = viewModel::togglePasswordVisibility,
                        onSubmit = viewModel::submitAuthForm,
                        onEnterWorkspacePreview = {
                            viewModel.enterWorkspacePreview()
                            navigateTo(JarvisDestination.HOME)
                        }
                    )
                }
                composable(JarvisDestination.PERMISSION_CENTER.route) {
                    PermissionCenterScreen(contentPadding = innerPadding)
                }
            }
        }
    }
}
