package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FolderSpecial
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.ui.components.JarvisGlassCard
import com.example.ui.components.JarvisPhaseNoticeBanner
import com.example.ui.components.JarvisSectionHeader
import com.example.ui.components.JarvisStatusPill
import com.example.ui.state.JarvisLanguage
import com.example.ui.state.JarvisUiState
import com.example.ui.theme.LocalJarvisColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DocumentAssistantScreen(contentPadding: PaddingValues) {
    val ext = LocalJarvisColors.current
    var actionMode by remember { mutableStateOf("Document Q&A") }
    val formats = listOf("PDF", "TXT", "DOCX", "XLSX", "CSV", "PPTX")

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "Document Assistant",
                subtitle = "PDF, TXT, DOCX, XLSX, CSV & PPTX reading, Q&A, translation & export",
                trailing = { JarvisStatusPill(text = "Phase 9 Docs", accentColor = ext.warning) }
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                Text("Supported File Formats", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(6.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    formats.forEach { fmt ->
                        JarvisStatusPill(text = fmt, accentColor = ext.cyanAccent, leadingDot = false)
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text("Document Action", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("Document Q&A", "Summarize", "Translate", "Extract Text", "Create Document").forEach { mode ->
                        FilterChip(
                            selected = actionMode == mode,
                            onClick = { actionMode = mode },
                            label = { Text(mode) }
                        )
                    }
                }
            }
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "Zero-Permission File Picker & Phase 9 Parsing",
                description = "Active workflow: $actionMode. Document upload via official Android Storage Access Framework & AI extraction activates in Phase 9.",
                phaseLabel = "Phase 9 Docs",
                icon = Icons.Outlined.Description
            )
        }
    }
}

@Composable
fun ProjectManagerScreen(contentPadding: PaddingValues) {
    val ext = LocalJarvisColors.current
    var search by remember { mutableStateOf("") }

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "Personal Project Manager",
                subtitle = "Create, rename, organize files, track versions & export workspaces",
                trailing = { JarvisStatusPill(text = "Phase 2 Sync", accentColor = ext.cyanAccent) }
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    label = { Text("Search Projects") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("project_search_input")
                )
            }
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "0 Synced Projects • Database Connects in Phase 2",
                description = "User-isolated PostgreSQL / cloud project persistence, version history, and ZIP export activate once Phase 2 backend database & auth are provisioned.",
                phaseLabel = "Phase 2 DB",
                icon = Icons.Outlined.FolderSpecial
            )
        }
    }
}

@Composable
fun OwnerDashboardScreen(
    contentPadding: PaddingValues,
    onOpenPermissions: () -> Unit
) {
    val ext = LocalJarvisColors.current
    val services = listOf(
        "Authentication & Cloud DB" to "Not Configured (Phase 2)",
        "Gemini AI Brain & Voice" to "Not Configured (Phase 3)",
        "Live Search & Knowledge" to "Not Configured (Phase 4)",
        "Coding & Web/App Builders" to "Not Configured (Phase 5)",
        "Remote Android Gradle CI" to "Not Configured (Phase 6)",
        "Image & Anime Studios" to "Not Configured (Phase 7)",
        "Video & 3D/CGI Services" to "Not Configured (Phase 8)",
        "Document & Admin Audit APIs" to "Not Configured (Phase 9)"
    )

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "Owner Admin Dashboard",
                subtitle = "Protected system health, API configuration matrix, user & audit overview",
                trailing = { JarvisStatusPill(text = "RBAC Locked", accentColor = ext.danger) }
            )
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "Server-Side Role Verification Required",
                description = "Admin controls, user management, and maintenance toggles are strictly locked behind server-side role verification (Phase 2/9). Frontend-only admin passwords are never used.",
                phaseLabel = "Security Gate",
                icon = Icons.Outlined.AdminPanelSettings
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                Text("API & Service Configuration Status", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    services.forEach { (service, status) ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = service,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(Modifier.width(8.dp))
                            JarvisStatusPill(
                                text = status,
                                accentColor = ext.warning,
                                leadingDot = false
                            )
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onOpenPermissions,
                    modifier = Modifier.testTag("owner_permissions_button")
                ) {
                    Text("View Android Permission Center")
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(
    contentPadding: PaddingValues,
    uiState: JarvisUiState,
    onToggleTheme: (Boolean) -> Unit,
    onSelectLanguage: (JarvisLanguage) -> Unit,
    onOpenAuth: () -> Unit,
    onOpenPermissions: () -> Unit
) {
    val ext = LocalJarvisColors.current

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "Settings & Preferences",
                subtitle = "Theme mode, multilingual preferences, account access & Android permissions",
                trailing = { JarvisStatusPill(text = "Phase 1 Live", accentColor = ext.success) }
            )
        }

        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                Text("Appearance", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (uiState.isDarkTheme) "Futuristic Dark Theme" else "Crisp Ice Light Theme",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Switch between Dark Command Navy and High-Contrast Light Mode.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = uiState.isDarkTheme,
                        onCheckedChange = onToggleTheme,
                        modifier = Modifier.testTag("settings_dark_theme_switch")
                    )
                }
            }
        }

        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("Preferred Language", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Current: ${uiState.selectedLanguage.displayName} (${uiState.selectedLanguage.nativeName})",
                    style = MaterialTheme.typography.bodySmall,
                    color = ext.cyanAccent
                )
                Spacer(Modifier.height(10.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    JarvisLanguage.entries.forEach { lang ->
                        FilterChip(
                            selected = uiState.selectedLanguage == lang,
                            onClick = { onSelectLanguage(lang) },
                            modifier = Modifier.testTag("settings_lang_${lang.code}"),
                            label = { Text("${lang.displayName} (${lang.nativeName})") }
                        )
                    }
                }
            }
        }

        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("Account, Privacy & Device Permissions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onOpenAuth,
                        modifier = Modifier.testTag("settings_open_auth_button")
                    ) {
                        Icon(Icons.Outlined.VerifiedUser, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Account & Sign In")
                    }
                    OutlinedButton(
                        onClick = onOpenPermissions,
                        modifier = Modifier.testTag("settings_open_permissions_button")
                    ) {
                        Icon(Icons.Outlined.Security, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Android Permission Center")
                    }
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Privacy Commitment: API keys and signing secrets are never shipped in client code. User memory editing, account deletion, and audit logs will be wired in Phase 2 & Phase 10.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private data class PermissionEntry(
    val name: String,
    val purpose: String,
    val manifestPermission: String?,
    val isPlayRestrictedOrNotRequired: Boolean = false,
    val customStatusLabel: String? = null
)

@Composable
fun PermissionCenterScreen(contentPadding: PaddingValues) {
    val ext = LocalJarvisColors.current
    val context = LocalContext.current

    val permissions = remember {
        listOf(
            PermissionEntry(
                name = "Microphone",
                purpose = "Required only when you activate Voice Assistant speech input (Phase 3).",
                manifestPermission = Manifest.permission.RECORD_AUDIO
            ),
            PermissionEntry(
                name = "Camera",
                purpose = "Optional visual reference capture when requested by user.",
                manifestPermission = Manifest.permission.CAMERA
            ),
            PermissionEntry(
                name = "Photos & Videos",
                purpose = "Uses zero-permission Android Photo Picker (no broad media storage permission required).",
                manifestPermission = null,
                isPlayRestrictedOrNotRequired = true,
                customStatusLabel = "Not Required (Photo Picker)"
            ),
            PermissionEntry(
                name = "Files & Documents",
                purpose = "Uses Android Storage Access Framework picker on demand.",
                manifestPermission = null,
                isPlayRestrictedOrNotRequired = true,
                customStatusLabel = "Not Required (SAF Picker)"
            ),
            PermissionEntry(
                name = "Notifications",
                purpose = "Build completion & assistant alerts (Android 13+ runtime permission).",
                manifestPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.POST_NOTIFICATIONS
                } else null,
                customStatusLabel = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) "Granted (OS < 13)" else null
            ),
            PermissionEntry(
                name = "Location",
                purpose = "Not requested in Phase 1; least-privilege policy active.",
                manifestPermission = Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PermissionEntry(
                name = "Bluetooth",
                purpose = "Optional audio headset routing on Android 12+.",
                manifestPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Manifest.permission.BLUETOOTH_CONNECT
                } else null,
                customStatusLabel = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) "Not Required (OS < 12)" else null
            ),
            PermissionEntry(
                name = "Contacts",
                purpose = "Not requested in Phase 1 (least-privilege privacy).",
                manifestPermission = Manifest.permission.READ_CONTACTS
            ),
            PermissionEntry(
                name = "Calendar",
                purpose = "Not requested in Phase 1 (least-privilege privacy).",
                manifestPermission = Manifest.permission.READ_CALENDAR
            )
        )
    }

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "Android Permission Center",
                subtitle = "Live OS permission status check via Android ContextCompat",
                trailing = { JarvisStatusPill(text = "Live Status", accentColor = ext.cyanAccent) }
            )
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "Least-Privilege Policy",
                description = "JARVIS AI only declares and requests permissions strictly required by active features. Broad media storage permissions are intentionally omitted in favor of Android Photo Picker.",
                phaseLabel = "Play Compliant",
                icon = Icons.Outlined.Security
            )
        }
        items(permissions.size) { index ->
            val item = permissions[index]
            val granted = item.manifestPermission?.let {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
            val statusText = when {
                item.customStatusLabel != null -> item.customStatusLabel
                granted == true -> "Granted"
                else -> "Not Granted / Restricted"
            }
            val statusColor = when {
                granted == true || item.isPlayRestrictedOrNotRequired -> ext.success
                item.customStatusLabel?.startsWith("Granted") == true -> ext.success
                else -> ext.warning
            }

            JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = item.purpose,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    JarvisStatusPill(text = statusText, accentColor = statusColor, leadingDot = false)
                }
            }
        }
    }
}
