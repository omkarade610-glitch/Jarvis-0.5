package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.navigation.JarvisDestination
import com.example.navigation.JarvisNavCategory
import com.example.ui.state.JarvisUiState
import com.example.ui.theme.LocalJarvisColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JarvisAdaptiveShell(
    currentDestination: JarvisDestination,
    uiState: JarvisUiState,
    onNavigate: (JarvisDestination) -> Unit,
    onToggleTheme: () -> Unit,
    onOpenNotifications: (Boolean) -> Unit,
    onOpenModuleDirectory: (Boolean) -> Unit,
    content: @Composable (PaddingValues, Boolean) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val ext = LocalJarvisColors.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            JarvisDrawerContent(
                currentDestination = currentDestination,
                uiState = uiState,
                onSelectDestination = { dest ->
                    scope.launch { drawerState.close() }
                    onNavigate(dest)
                },
                onClose = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.65f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        ) {
            val isWideScreen = maxWidth >= 600.dp

            Row(modifier = Modifier.fillMaxSize()) {
                if (isWideScreen) {
                    JarvisSideRail(
                        currentDestination = currentDestination,
                        onNavigate = onNavigate,
                        onOpenAllModules = { scope.launch { drawerState.open() } }
                    )
                }

                Scaffold(
                    modifier = Modifier.weight(1f),
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0f),
                    contentWindowInsets = WindowInsets.statusBars,
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = ext.glassSurfaceStrong,
                                titleContentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            navigationIcon = {
                                IconButton(
                                    onClick = { scope.launch { drawerState.open() } },
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize()
                                        .testTag("open_navigation_drawer_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "Open all 17 JARVIS AI modules"
                                    )
                                }
                            },
                            title = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    JarvisLogoMark(size = 28.dp)
                                    Column {
                                        Text(
                                            text = currentDestination.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = "JARVIS AI • ${uiState.selectedLanguage.displayName}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = ext.cyanAccent,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            },
                            actions = {
                                IconButton(
                                    onClick = { onOpenModuleDirectory(true) },
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize()
                                        .testTag("all_modules_grid_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Apps,
                                        contentDescription = "Command hub directory"
                                    )
                                }
                                IconButton(
                                    onClick = onToggleTheme,
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize()
                                        .testTag("toggle_theme_button")
                                ) {
                                    Icon(
                                        imageVector = if (uiState.isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                                        contentDescription = if (uiState.isDarkTheme) "Switch to light theme" else "Switch to dark theme"
                                    )
                                }
                                IconButton(
                                    onClick = { onOpenNotifications(true) },
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize()
                                        .testTag("notifications_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Notifications,
                                        contentDescription = "System notifications"
                                    )
                                }
                                IconButton(
                                    onClick = { onNavigate(JarvisDestination.AUTH) },
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize()
                                        .testTag("profile_auth_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.AccountCircle,
                                        contentDescription = "Account and authentication"
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        if (!isWideScreen) {
                            NavigationBar(
                                containerColor = ext.glassSurfaceStrong,
                                windowInsets = WindowInsets.navigationBars
                            ) {
                                JarvisDestination.bottomBarDestinations.forEach { dest ->
                                    val selected = currentDestination == dest
                                    NavigationBarItem(
                                        selected = selected,
                                        onClick = { onNavigate(dest) },
                                        modifier = Modifier.testTag("bottom_nav_${dest.route}"),
                                        icon = {
                                            Icon(
                                                imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                                                contentDescription = dest.title
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = dest.shortLabel,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = ext.cyanAccent,
                                            selectedTextColor = ext.cyanAccent,
                                            indicatorColor = ext.cyanAccent.copy(alpha = 0.16f),
                                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    content(innerPadding, isWideScreen)
                }
            }
        }
    }

    if (uiState.showNotificationsSheet) {
        ModalBottomSheet(
            onDismissRequest = { onOpenNotifications(false) },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "System Notifications",
                    style = MaterialTheme.typography.titleLarge
                )
                JarvisPhaseNoticeBanner(
                    title = "Phase 1 Foundation Active",
                    description = "Design system, adaptive navigation across all 17 workspaces, authentication UI, and live Android permission diagnostics are ready. Real-time push notifications and cloud alerts activate in Phase 2+.",
                    phaseLabel = "Phase 1"
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }

    if (uiState.showModuleDirectorySheet) {
        ModalBottomSheet(
            onDismissRequest = { onOpenModuleDirectory(false) },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            AllModulesBottomSheetContent(
                currentDestination = currentDestination,
                onSelect = { dest ->
                    onOpenModuleDirectory(false)
                    onNavigate(dest)
                }
            )
        }
    }
}

@Composable
private fun JarvisSideRail(
    currentDestination: JarvisDestination,
    onNavigate: (JarvisDestination) -> Unit,
    onOpenAllModules: () -> Unit
) {
    val ext = LocalJarvisColors.current
    val railItems = listOf(
        JarvisDestination.HOME,
        JarvisDestination.AI_ASSISTANT,
        JarvisDestination.VOICE_ASSISTANT,
        JarvisDestination.LIVE_SEARCH,
        JarvisDestination.CODING_STUDIO,
        JarvisDestination.ANIME_STUDIO,
        JarvisDestination.PROJECT_MANAGER,
        JarvisDestination.SETTINGS
    )

    NavigationRail(
        containerColor = ext.glassSurfaceStrong,
        modifier = Modifier.fillMaxHeight(),
        header = {
            IconButton(
                onClick = onOpenAllModules,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .minimumInteractiveComponentSize()
                    .testTag("rail_all_modules_button")
            ) {
                Icon(
                    imageVector = Icons.Filled.Apps,
                    contentDescription = "All 17 modules",
                    tint = ext.cyanAccent
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(railItems) { dest ->
                val selected = currentDestination == dest
                NavigationRailItem(
                    selected = selected,
                    onClick = { onNavigate(dest) },
                    modifier = Modifier.testTag("rail_nav_${dest.route}"),
                    icon = {
                        Icon(
                            imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                            contentDescription = dest.title
                        )
                    },
                    label = {
                        Text(
                            text = dest.shortLabel,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = ext.cyanAccent,
                        selectedTextColor = ext.cyanAccent,
                        indicatorColor = ext.cyanAccent.copy(alpha = 0.16f)
                    )
                )
            }
        }
    }
}

@Composable
private fun JarvisDrawerContent(
    currentDestination: JarvisDestination,
    uiState: JarvisUiState,
    onSelectDestination: (JarvisDestination) -> Unit,
    onClose: () -> Unit
) {
    val ext = LocalJarvisColors.current
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.widthIn(max = 330.dp)
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    JarvisLogoMark(size = 38.dp)
                    Column {
                        Text(
                            text = "JARVIS AI",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "17 Command Workspaces",
                            style = MaterialTheme.typography.labelMedium,
                            color = ext.cyanAccent
                        )
                    }
                }
                IconButton(onClick = onClose) {
                    Icon(Icons.Filled.Close, contentDescription = "Close navigation drawer")
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                JarvisNavCategory.entries.forEach { category ->
                    val itemsInCategory = JarvisDestination.mainDestinations.filter { it.category == category }
                    if (itemsInCategory.isNotEmpty()) {
                        item(key = "header_${category.name}") {
                            Text(
                                text = category.label.uppercase(),
                                style = MaterialTheme.typography.labelMedium,
                                color = ext.cyanAccent,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                        items(itemsInCategory, key = { it.route }) { dest ->
                            val selected = currentDestination == dest
                            NavigationDrawerItem(
                                label = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = dest.title,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Spacer(Modifier.width(6.dp))
                                        Text(
                                            text = dest.phaseBadge,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (selected) ext.cyanAccent else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                },
                                selected = selected,
                                onClick = { onSelectDestination(dest) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                                        contentDescription = dest.title
                                    )
                                },
                                modifier = Modifier.testTag("drawer_nav_${dest.route}"),
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = ext.cyanAccent.copy(alpha = 0.14f),
                                    selectedIconColor = ext.cyanAccent,
                                    selectedTextColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }

                item(key = "extra_tools_header") {
                    Spacer(Modifier.height(6.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Text(
                        text = "SECURITY & ACCESS",
                        style = MaterialTheme.typography.labelMedium,
                        color = ext.purpleAccent,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }

                item(key = "drawer_auth") {
                    NavigationDrawerItem(
                        label = { Text("Account & Sign In") },
                        selected = currentDestination == JarvisDestination.AUTH,
                        onClick = { onSelectDestination(JarvisDestination.AUTH) },
                        icon = { Icon(Icons.Outlined.AccountCircle, contentDescription = "Account access") },
                        modifier = Modifier.testTag("drawer_nav_auth")
                    )
                }

                item(key = "drawer_permissions") {
                    NavigationDrawerItem(
                        label = { Text("Android Permission Center") },
                        selected = currentDestination == JarvisDestination.PERMISSION_CENTER,
                        onClick = { onSelectDestination(JarvisDestination.PERMISSION_CENTER) },
                        icon = { Icon(Icons.Outlined.Security, contentDescription = "Android Permission Center") },
                        modifier = Modifier.testTag("drawer_nav_permission_center")
                    )
                }
            }

            if (uiState.auth.isPreviewSessionActive) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(ext.cyanAccent.copy(alpha = 0.10f))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Workspace Preview Mode • Cloud Auth Pending Phase 2",
                        style = MaterialTheme.typography.labelSmall,
                        color = ext.cyanAccent
                    )
                }
            }
        }
    }
}

@Composable
private fun AllModulesBottomSheetContent(
    currentDestination: JarvisDestination,
    onSelect: (JarvisDestination) -> Unit
) {
    val ext = LocalJarvisColors.current
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "JARVIS AI Module Directory (17 Workspaces)",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Tap any workspace to inspect its responsive layout and Phase roadmap.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(JarvisDestination.mainDestinations, key = { it.route }) { dest ->
            val selected = currentDestination == dest
            JarvisGlassCard(
                onClick = { onSelect(dest) },
                highlightBorder = selected,
                contentPadding = 12.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("directory_card_${dest.route}")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = dest.selectedIcon,
                        contentDescription = dest.title,
                        tint = if (selected) ext.cyanAccent else MaterialTheme.colorScheme.primary
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = dest.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = dest.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    JarvisStatusPill(
                        text = dest.phaseBadge,
                        accentColor = if (dest == JarvisDestination.HOME || dest == JarvisDestination.SETTINGS) ext.success else ext.cyanAccent,
                        leadingDot = false
                    )
                }
            }
        }
        item { Spacer(Modifier.height(16.dp)) }
    }
}
