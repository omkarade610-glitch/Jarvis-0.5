package com.example.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Brush
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FolderSpecial
import androidx.compose.material.icons.outlined.GraphicEq
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.MovieCreation
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material.icons.outlined.ViewInAr
import androidx.compose.ui.graphics.vector.ImageVector

enum class JarvisNavCategory(val label: String) {
    CORE("Core Intelligence"),
    BUILDERS("Code & App Builders"),
    CREATIVE("Creative & Media Studios"),
    KNOWLEDGE("Knowledge & Workspace"),
    SYSTEM("Admin & System")
}

enum class JarvisDestination(
    val route: String,
    val title: String,
    val shortLabel: String,
    val subtitle: String,
    val category: JarvisNavCategory,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val phaseBadge: String,
    val isMainNavItem: Boolean = true
) {
    // 1. Home
    HOME(
        route = "home",
        title = "Home",
        shortLabel = "Home",
        subtitle = "Command Center Overview",
        category = JarvisNavCategory.CORE,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        phaseBadge = "Phase 1 Live"
    ),
    // 2. AI Assistant
    AI_ASSISTANT(
        route = "ai_assistant",
        title = "AI Assistant",
        shortLabel = "AI Chat",
        subtitle = "Multilingual Neural Brain",
        category = JarvisNavCategory.CORE,
        selectedIcon = Icons.Filled.AutoAwesome,
        unselectedIcon = Icons.Outlined.AutoAwesome,
        phaseBadge = "Phase 3 Gemini"
    ),
    // 3. Voice Assistant
    VOICE_ASSISTANT(
        route = "voice_assistant",
        title = "Voice Assistant",
        shortLabel = "Voice",
        subtitle = "Speech, Waveform & TTS",
        category = JarvisNavCategory.CORE,
        selectedIcon = Icons.Filled.GraphicEq,
        unselectedIcon = Icons.Outlined.GraphicEq,
        phaseBadge = "Phase 3 Voice"
    ),
    // 4. Live Search
    LIVE_SEARCH(
        route = "live_search",
        title = "Live Search",
        shortLabel = "Search",
        subtitle = "Grounded Web & News",
        category = JarvisNavCategory.CORE,
        selectedIcon = Icons.Filled.Public,
        unselectedIcon = Icons.Outlined.Public,
        phaseBadge = "Phase 4 Search"
    ),
    // 5. Knowledge Center
    KNOWLEDGE_CENTER(
        route = "knowledge_center",
        title = "Knowledge Center",
        shortLabel = "Knowledge",
        subtitle = "Notes, Memory & Docs",
        category = JarvisNavCategory.KNOWLEDGE,
        selectedIcon = Icons.Filled.LibraryBooks,
        unselectedIcon = Icons.Outlined.LibraryBooks,
        phaseBadge = "Phase 4 Vault"
    ),
    // 6. Coding Studio
    CODING_STUDIO(
        route = "coding_studio",
        title = "Coding Studio",
        shortLabel = "Code",
        subtitle = "16+ Language IDE & Git",
        category = JarvisNavCategory.BUILDERS,
        selectedIcon = Icons.Filled.Code,
        unselectedIcon = Icons.Outlined.Code,
        phaseBadge = "Phase 5 IDE"
    ),
    // 7. Website Builder
    WEBSITE_BUILDER(
        route = "website_builder",
        title = "Website Builder",
        shortLabel = "Web",
        subtitle = "Full-Stack Web Architect",
        category = JarvisNavCategory.BUILDERS,
        selectedIcon = Icons.Filled.Language,
        unselectedIcon = Icons.Outlined.Language,
        phaseBadge = "Phase 5 Web"
    ),
    // 8. App Builder
    APP_BUILDER(
        route = "app_builder",
        title = "App Builder",
        shortLabel = "Apps",
        subtitle = "Native & Mobile Blueprint",
        category = JarvisNavCategory.BUILDERS,
        selectedIcon = Icons.Filled.PhoneAndroid,
        unselectedIcon = Icons.Outlined.PhoneAndroid,
        phaseBadge = "Phase 5 Apps"
    ),
    // 9. APK Builder
    APK_BUILDER(
        route = "apk_builder",
        title = "APK Builder",
        shortLabel = "APK",
        subtitle = "Remote Gradle CI Builds",
        category = JarvisNavCategory.BUILDERS,
        selectedIcon = Icons.Filled.Android,
        unselectedIcon = Icons.Outlined.Android,
        phaseBadge = "Phase 6 Gradle"
    ),
    // 10. Image Studio
    IMAGE_STUDIO(
        route = "image_studio",
        title = "Image Studio",
        shortLabel = "Images",
        subtitle = "AI Art & Editing Canvas",
        category = JarvisNavCategory.CREATIVE,
        selectedIcon = Icons.Filled.Image,
        unselectedIcon = Icons.Outlined.Image,
        phaseBadge = "Phase 7 Image"
    ),
    // 11. Anime Studio
    ANIME_STUDIO(
        route = "anime_studio",
        title = "Anime Studio",
        shortLabel = "Anime",
        subtitle = "Graphite & Manga Art",
        category = JarvisNavCategory.CREATIVE,
        selectedIcon = Icons.Filled.Brush,
        unselectedIcon = Icons.Outlined.Brush,
        phaseBadge = "Phase 7 Anime"
    ),
    // 12. Video Studio
    VIDEO_STUDIO(
        route = "video_studio",
        title = "Video Studio",
        shortLabel = "Video",
        subtitle = "Storyboard & Render Pipe",
        category = JarvisNavCategory.CREATIVE,
        selectedIcon = Icons.Filled.Videocam,
        unselectedIcon = Icons.Outlined.Videocam,
        phaseBadge = "Phase 8 Video"
    ),
    // 13. 3D / CGI Studio
    CGI_STUDIO(
        route = "cgi_studio",
        title = "3D / CGI Studio",
        shortLabel = "3D CGI",
        subtitle = "Scenes, Rigs & Concepts",
        category = JarvisNavCategory.CREATIVE,
        selectedIcon = Icons.Filled.ViewInAr,
        unselectedIcon = Icons.Outlined.ViewInAr,
        phaseBadge = "Phase 8 3D"
    ),
    // 14. Document Assistant
    DOCUMENT_ASSISTANT(
        route = "document_assistant",
        title = "Document Assistant",
        shortLabel = "Docs",
        subtitle = "PDF, DOCX, XLSX & Q&A",
        category = JarvisNavCategory.KNOWLEDGE,
        selectedIcon = Icons.Filled.Description,
        unselectedIcon = Icons.Outlined.Description,
        phaseBadge = "Phase 9 Docs"
    ),
    // 15. Project Manager
    PROJECT_MANAGER(
        route = "project_manager",
        title = "Project Manager",
        shortLabel = "Projects",
        subtitle = "Versions, Files & Export",
        category = JarvisNavCategory.KNOWLEDGE,
        selectedIcon = Icons.Filled.FolderSpecial,
        unselectedIcon = Icons.Outlined.FolderSpecial,
        phaseBadge = "Phase 2 Sync"
    ),
    // 16. Owner Dashboard
    OWNER_DASHBOARD(
        route = "owner_dashboard",
        title = "Owner Dashboard",
        shortLabel = "Owner",
        subtitle = "System Health & Roles",
        category = JarvisNavCategory.SYSTEM,
        selectedIcon = Icons.Filled.AdminPanelSettings,
        unselectedIcon = Icons.Outlined.AdminPanelSettings,
        phaseBadge = "Phase 9 Admin"
    ),
    // 17. Settings
    SETTINGS(
        route = "settings",
        title = "Settings",
        shortLabel = "Settings",
        subtitle = "Theme, Language & Auth",
        category = JarvisNavCategory.SYSTEM,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        phaseBadge = "Phase 1 Live"
    ),

    // Sub-destinations supporting full architecture
    AUTH(
        route = "auth",
        title = "Account Access",
        shortLabel = "Auth",
        subtitle = "Sign In, Sign Up & Recovery",
        category = JarvisNavCategory.SYSTEM,
        selectedIcon = Icons.Filled.Security,
        unselectedIcon = Icons.Outlined.Security,
        phaseBadge = "Phase 1 UI / Phase 2 Auth",
        isMainNavItem = false
    ),
    ANIME_SERIES_CREATOR(
        route = "anime_series_creator",
        title = "Anime Series Creator",
        shortLabel = "Series",
        subtitle = "Story, Episodes & Timeline",
        category = JarvisNavCategory.CREATIVE,
        selectedIcon = Icons.Filled.MovieCreation,
        unselectedIcon = Icons.Outlined.MovieCreation,
        phaseBadge = "Phase 8 Production",
        isMainNavItem = false
    ),
    PERMISSION_CENTER(
        route = "permission_center",
        title = "Permission Center",
        shortLabel = "Permissions",
        subtitle = "Live Android Runtime Access",
        category = JarvisNavCategory.SYSTEM,
        selectedIcon = Icons.Filled.Security,
        unselectedIcon = Icons.Outlined.Security,
        phaseBadge = "Live Device Check",
        isMainNavItem = false
    );

    companion object {
        val mainDestinations: List<JarvisDestination> = entries.filter { it.isMainNavItem }
        val bottomBarDestinations: List<JarvisDestination> = listOf(
            HOME, AI_ASSISTANT, VOICE_ASSISTANT, CODING_STUDIO, SETTINGS
        )

        fun fromRoute(route: String?): JarvisDestination =
            entries.find { it.route == route } ?: HOME
    }
}
