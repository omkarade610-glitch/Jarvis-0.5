package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.navigation.JarvisDestination
import com.example.ui.components.JarvisAnimatedOrb
import com.example.ui.components.JarvisGlassCard
import com.example.ui.components.JarvisPhaseNoticeBanner
import com.example.ui.components.JarvisSectionHeader
import com.example.ui.components.JarvisStatusPill
import com.example.ui.components.JarvisWaveform
import com.example.ui.state.JarvisUiState
import com.example.ui.state.OrbActivityState
import com.example.ui.theme.LocalJarvisColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeDashboardScreen(
    contentPadding: PaddingValues,
    uiState: JarvisUiState,
    onOrbStateChange: (OrbActivityState) -> Unit,
    onQuickCommandChange: (String) -> Unit,
    onNavigate: (JarvisDestination) -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenModuleDirectory: () -> Unit
) {
    val ext = LocalJarvisColors.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .widthIn(max = 1040.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // 1. Hero Command Center Card
            item {
                JarvisGlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    highlightBorder = true,
                    contentPadding = 0.dp
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.img_command_banner),
                            contentDescription = "JARVIS AI futuristic command center backdrop",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .matchParentSize()
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            ext.heroScrim.copy(alpha = 0.78f),
                                            ext.heroScrim.copy(alpha = 0.94f)
                                        )
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    JarvisStatusPill(
                                        text = uiState.orbState.statusText,
                                        accentColor = ext.cyanAccent
                                    )
                                    Spacer(Modifier.height(10.dp))
                                    Text(
                                        text = "Welcome to JARVIS AI",
                                        style = MaterialTheme.typography.displayMedium,
                                        color = Color.White
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = "Multilingual personal AI command center • ${uiState.selectedLanguage.displayName} (${uiState.selectedLanguage.nativeName})",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White.copy(alpha = 0.82f)
                                    )
                                }
                                Spacer(Modifier.width(12.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.img_jarvis_avatar),
                                    contentDescription = "Original JARVIS AI orb avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                )
                            }

                            // Interactive Orb + Waveform Stage
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                JarvisAnimatedOrb(
                                    orbState = uiState.orbState,
                                    orbSize = 110.dp
                                )
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Orb Telemetry Preview",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = Color.White
                                    )
                                    JarvisWaveform(
                                        orbState = uiState.orbState,
                                        height = 42.dp
                                    )
                                    FlowRow(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        OrbActivityState.entries.forEach { state ->
                                            FilterChip(
                                                selected = uiState.orbState == state,
                                                onClick = { onOrbStateChange(state) },
                                                modifier = Modifier.testTag("orb_state_${state.name.lowercase()}"),
                                                label = {
                                                    Text(
                                                        text = state.label,
                                                        style = MaterialTheme.typography.labelSmall
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 2. AI Chat Input + Microphone + Voice Mode Bar
            item {
                JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Quick Command Prompt",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.quickCommandDraft,
                            onValueChange = onQuickCommandChange,
                            placeholder = { Text("Ask JARVIS, plan a build, or jump to AI Assistant...") },
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("dashboard_ai_input")
                        )
                        IconButton(
                            onClick = { onNavigate(JarvisDestination.VOICE_ASSISTANT) },
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .clip(RoundedCornerShape(12.dp))
                                .background(ext.cyanAccent.copy(alpha = 0.14f))
                                .testTag("dashboard_mic_button")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Mic,
                                contentDescription = "Open Voice Assistant microphone",
                                tint = ext.cyanAccent
                            )
                        }
                        IconButton(
                            onClick = { onNavigate(JarvisDestination.AI_ASSISTANT) },
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .clip(RoundedCornerShape(12.dp))
                                .background(ext.electricAccent)
                                .testTag("dashboard_send_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Open AI Assistant",
                                tint = Color.White
                            )
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onNavigate(JarvisDestination.VOICE_ASSISTANT) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("dashboard_voice_mode_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ext.electricAccent,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(Icons.Filled.GraphicEq, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Voice Mode")
                        }
                        OutlinedButton(
                            onClick = onOpenModuleDirectory,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("dashboard_all_modules_button")
                        ) {
                            Text("All 17 Modules")
                        }
                    }
                }
            }

            // 3. Quick Action Shortcuts (Responsive Grid)
            item {
                JarvisSectionHeader(
                    title = "Quick Action Shortcuts",
                    subtitle = "Direct access to core intelligence, studios, and builders",
                    trailing = {
                        TextButton(onClick = onOpenModuleDirectory) {
                            Text("View All 17")
                        }
                    }
                )
            }

            item {
                val quickDestinations = listOf(
                    JarvisDestination.AI_ASSISTANT,
                    JarvisDestination.VOICE_ASSISTANT,
                    JarvisDestination.LIVE_SEARCH,
                    JarvisDestination.KNOWLEDGE_CENTER,
                    JarvisDestination.CODING_STUDIO,
                    JarvisDestination.WEBSITE_BUILDER,
                    JarvisDestination.APP_BUILDER,
                    JarvisDestination.APK_BUILDER,
                    JarvisDestination.IMAGE_STUDIO,
                    JarvisDestination.ANIME_STUDIO,
                    JarvisDestination.VIDEO_STUDIO,
                    JarvisDestination.CGI_STUDIO
                )
                AdaptiveDestinationGrid(
                    destinations = quickDestinations,
                    onNavigate = onNavigate
                )
            }

            // 4. Dashboard Overview Widgets (Honest Empty / Phase 1 States)
            item {
                JarvisSectionHeader(
                    title = "Workspace Status & Telemetry",
                    subtitle = "Real backend integrations activate sequentially in Phases 2–9"
                )
            }

            item {
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val columns = if (maxWidth >= 680.dp) 2 else 1
                    val cards = listOf(
                        DashboardSummaryCardData(
                            title = "Recent Conversations",
                            emptyMessage = "No conversations yet. Gemini Brain & encrypted history connect in Phase 3.",
                            actionLabel = "Open AI Assistant",
                            destination = JarvisDestination.AI_ASSISTANT,
                            icon = Icons.Outlined.ChatBubbleOutline
                        ),
                        DashboardSummaryCardData(
                            title = "Recent Projects",
                            emptyMessage = "No synced projects yet. User database & project storage connect in Phase 2.",
                            actionLabel = "Project Manager",
                            destination = JarvisDestination.PROJECT_MANAGER,
                            icon = Icons.Outlined.FolderOpen
                        ),
                        DashboardSummaryCardData(
                            title = "Saved Knowledge",
                            emptyMessage = "Knowledge vault is empty. Document ingestion & user-controlled memory arrive in Phase 4.",
                            actionLabel = "Knowledge Center",
                            destination = JarvisDestination.KNOWLEDGE_CENTER,
                            icon = Icons.Outlined.LibraryBooks
                        ),
                        DashboardSummaryCardData(
                            title = "Recent Generated Images",
                            emptyMessage = "No artwork generated yet. Image & Anime Studios connect to real models in Phase 7.",
                            actionLabel = "Open Anime Studio",
                            destination = JarvisDestination.ANIME_STUDIO,
                            icon = Icons.Outlined.Image
                        )
                    )

                    if (columns == 2) {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            cards.chunked(2).forEach { pair ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    pair.forEach { card ->
                                        SummaryStatusCard(
                                            data = card,
                                            onClick = { onNavigate(card.destination) },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            cards.forEach { card ->
                                SummaryStatusCard(
                                    data = card,
                                    onClick = { onNavigate(card.destination) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }

            // 5. Usage, Notifications, Profile & Permissions Footer
            item {
                JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Usage & Account Status",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Session: Phase 1 UI Preview • API Calls: 0 (Backend Pending Phase 2–3)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        JarvisStatusPill(text = "0% Quota", accentColor = ext.success)
                    }
                    Spacer(Modifier.height(12.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = onOpenNotifications,
                            modifier = Modifier.testTag("dashboard_notifications_chip")
                        ) {
                            Icon(Icons.Outlined.NotificationsNone, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Notifications")
                        }
                        OutlinedButton(
                            onClick = { onNavigate(JarvisDestination.AUTH) },
                            modifier = Modifier.testTag("dashboard_profile_chip")
                        ) {
                            Icon(Icons.Outlined.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Profile & Auth")
                        }
                        OutlinedButton(
                            onClick = { onNavigate(JarvisDestination.PERMISSION_CENTER) },
                            modifier = Modifier.testTag("dashboard_permissions_chip")
                        ) {
                            Icon(Icons.Outlined.Security, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Permissions")
                        }
                    }
                }
            }

            item {
                JarvisPhaseNoticeBanner(
                    title = "Phase 1 Complete • Awaiting Phase 2 Authorization",
                    description = "All 17 navigation modules, custom dark/light design system, authentication screens, and responsive layouts are active. No mock AI or fake backend calls are simulated.",
                    phaseLabel = "Phase 1 Ready"
                )
            }
        }
    }
}

@Composable
private fun AdaptiveDestinationGrid(
    destinations: List<JarvisDestination>,
    onNavigate: (JarvisDestination) -> Unit
) {
    val ext = LocalJarvisColors.current
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val columns = when {
            maxWidth >= 820.dp -> 4
            maxWidth >= 520.dp -> 3
            else -> 2
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            destinations.chunked(columns).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowItems.forEach { dest ->
                        JarvisGlassCard(
                            onClick = { onNavigate(dest) },
                            contentPadding = 12.dp,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("quick_card_${dest.route}")
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = dest.selectedIcon,
                                    contentDescription = dest.title,
                                    tint = ext.cyanAccent,
                                    modifier = Modifier.size(22.dp)
                                )
                                Text(
                                    text = dest.phaseBadge,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ext.purpleAccent,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = dest.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = dest.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    repeat(columns - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

private data class DashboardSummaryCardData(
    val title: String,
    val emptyMessage: String,
    val actionLabel: String,
    val destination: JarvisDestination,
    val icon: ImageVector
)

@Composable
private fun SummaryStatusCard(
    data: DashboardSummaryCardData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val ext = LocalJarvisColors.current
    JarvisGlassCard(
        onClick = onClick,
        modifier = modifier.testTag("summary_card_${data.destination.route}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = data.icon,
                contentDescription = data.title,
                tint = ext.cyanAccent
            )
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = data.emptyMessage,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "${data.actionLabel} →",
            style = MaterialTheme.typography.labelLarge,
            color = ext.cyanAccent
        )
    }
}
