package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.components.JarvisGlassCard
import com.example.ui.components.JarvisPhaseNoticeBanner
import com.example.ui.components.JarvisSectionHeader
import com.example.ui.components.JarvisStatusPill
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.LocalJarvisColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CodingStudioScreen(contentPadding: PaddingValues) {
    val ext = LocalJarvisColors.current
    val languages = listOf(
        "Kotlin", "TypeScript", "Python", "HTML", "CSS", "JavaScript",
        "Java", "C", "C++", "C#", "PHP", "SQL", "Dart", "Swift", "Go", "Rust"
    )
    var selectedLang by remember { mutableStateOf("Kotlin") }
    var activeTab by remember { mutableStateOf("Editor") }
    var codeDraft by remember {
        mutableStateOf("// JARVIS Coding Studio (Phase 1 Layout)\n// Select any of 16 languages above.\nfun main() {\n    println(\"JARVIS AI Studio Ready\")\n}")
    }

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "Coding Studio",
                subtitle = "16+ programming languages, file manager, debugging, GitHub & version history",
                trailing = { JarvisStatusPill(text = "Phase 5 IDE", accentColor = ext.warning) }
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("Supported Languages (16)", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    languages.forEach { lang ->
                        FilterChip(
                            selected = selectedLang == lang,
                            onClick = { selectedLang = lang },
                            modifier = Modifier.testTag("coding_lang_${lang.lowercase()}"),
                            label = { Text(lang, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
            }
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Editor", "Files", "Preview", "Build Logs", "Tests").forEach { tab ->
                        FilterChip(
                            selected = activeTab == tab,
                            onClick = { activeTab = tab },
                            label = { Text(tab) }
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                if (activeTab == "Editor") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(ext.codeBackground)
                            .padding(12.dp)
                    ) {
                        OutlinedTextField(
                            value = codeDraft,
                            onValueChange = { codeDraft = it },
                            label = { Text("$selectedLang Workspace Buffer") },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(fontFamily = JetBrainsMonoFontFamily),
                            minLines = 6,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("coding_editor_buffer")
                        )
                    }
                } else {
                    Text(
                        text = "$activeTab panel ready. Real code generation, GitHub sync, and verified execution telemetry will be integrated in Phase 5.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "Verified Execution Policy",
                description = "JARVIS AI only reports test passes or build success when a real execution environment confirms it. Phase 5 activates full IDE & GitHub workflows.",
                phaseLabel = "Phase 5 IDE",
                icon = Icons.Outlined.Code
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WebsiteBuilderScreen(contentPadding: PaddingValues) {
    val ext = LocalJarvisColors.current
    var promptDraft by remember { mutableStateOf("") }
    var viewportMode by remember { mutableStateOf("Mobile") }

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "AI Website Builder",
                subtitle = "Natural language to full-stack frontend, backend, database & live preview",
                trailing = { JarvisStatusPill(text = "Phase 5 Web", accentColor = ext.warning) }
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                Text("Describe Your Web Application", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = promptDraft,
                    onValueChange = { promptDraft = it },
                    placeholder = { Text("Describe layout, database models, authentication & API routes...") },
                    minLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("website_builder_prompt")
                )
                Spacer(Modifier.height(10.dp))
                Text("Responsive Viewport Target", style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(6.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Mobile", "Tablet", "Desktop").forEach { mode ->
                        FilterChip(
                            selected = viewportMode == mode,
                            onClick = { viewportMode = mode },
                            label = { Text(mode) }
                        )
                    }
                }
            }
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "Real Code Generation Pipeline",
                description = "Target viewport: $viewportMode. Full project scaffolding, editable source files, and GitHub export connect in Phase 5.",
                phaseLabel = "Phase 5 Web",
                icon = Icons.Outlined.Language
            )
        }
    }
}

@Composable
fun AppBuilderScreen(contentPadding: PaddingValues) {
    val ext = LocalJarvisColors.current
    var appIdea by remember { mutableStateOf("") }

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "AI App Builder",
                subtitle = "Mobile architecture, screen flow, auth, API integration & state planning",
                trailing = { JarvisStatusPill(text = "Phase 5 Apps", accentColor = ext.warning) }
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                OutlinedTextField(
                    value = appIdea,
                    onValueChange = { appIdea = it },
                    label = { Text("Describe Mobile App Features & Screens") },
                    minLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("app_builder_input")
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Architecture Blueprint Checklist: Navigation Graph • Auth Flow • Encrypted Storage • API Client • Responsive Adaptive UI",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "App Scaffolding & Code Export",
                description = "Generates real modular mobile source code rather than static mockups once Phase 5 App Builder services are connected.",
                phaseLabel = "Phase 5 Apps",
                icon = Icons.Outlined.PhoneAndroid
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ApkBuilderScreen(
    contentPadding: PaddingValues,
    onOpenPermissions: () -> Unit
) {
    val ext = LocalJarvisColors.current
    var appName by remember { mutableStateOf("JARVIS AI") }
    var packageName by remember { mutableStateOf("com.aistudio.jarvisai.xkqpzm") }
    var versionName by remember { mutableStateOf("1.0.0") }
    var buildType by remember { mutableStateOf("Debug APK") }

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "Android APK Builder",
                subtitle = "Remote Gradle CI pipeline, manifest permissions, Debug/Release APK & AAB",
                trailing = { JarvisStatusPill(text = "Phase 6 Gradle", accentColor = ext.warning) }
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                Text("Android Build Configuration", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = appName,
                    onValueChange = { appName = it },
                    label = { Text("Application Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = packageName,
                    onValueChange = { packageName = it },
                    label = { Text("Application ID / Package Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = versionName,
                    onValueChange = { versionName = it },
                    label = { Text("Version Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                Text("Artifact Target", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(6.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Debug APK", "Release APK", "Android App Bundle (AAB)").forEach { target ->
                        FilterChip(
                            selected = buildType == target,
                            onClick = { buildType = target },
                            label = { Text(target) }
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onOpenPermissions,
                    modifier = Modifier.testTag("apk_permissions_link_button")
                ) {
                    Icon(Icons.Outlined.Security, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Inspect Live Android Permission Center")
                }
            }
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "Remote Gradle Runner Required (Phase 6)",
                description = "Selected artifact: $buildType. APK download links are strictly hidden until a real remote GitHub Actions / Gradle build produces and verifies an actual signed artifact.",
                phaseLabel = "Phase 6 CI/CD",
                icon = Icons.Outlined.Android
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Outlined.Build, contentDescription = "Build history", tint = ext.cyanAccent)
                    Column {
                        Text("Build History: 0 Artifacts", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = "No remote Gradle builds triggered yet. Signing keys remain protected server-side.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
