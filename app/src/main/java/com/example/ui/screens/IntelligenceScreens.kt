package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.components.JarvisAnimatedOrb
import com.example.ui.components.JarvisGlassCard
import com.example.ui.components.JarvisPhaseNoticeBanner
import com.example.ui.components.JarvisSectionHeader
import com.example.ui.components.JarvisStatusPill
import com.example.ui.components.JarvisWaveform
import com.example.ui.state.JarvisLanguage
import com.example.ui.state.JarvisUiState
import com.example.ui.state.OrbActivityState
import com.example.ui.theme.LocalJarvisColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AiAssistantScreen(
    contentPadding: PaddingValues,
    uiState: JarvisUiState,
    onLanguageSelected: (JarvisLanguage) -> Unit,
    onDraftChange: (String) -> Unit,
    onOpenVoiceAssistant: () -> Unit
) {
    val ext = LocalJarvisColors.current
    var submittedNote by remember { mutableStateOf<String?>(null) }

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "JARVIS AI Brain",
                subtitle = "Reasoning, code, math, translation, research & multilingual conversations",
                trailing = { JarvisStatusPill(text = "Phase 3 API", accentColor = ext.warning) }
            )
        }

        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("Preferred Response Language", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    JarvisLanguage.entries.forEach { lang ->
                        FilterChip(
                            selected = uiState.selectedLanguage == lang,
                            onClick = { onLanguageSelected(lang) },
                            modifier = Modifier.testTag("ai_lang_${lang.code}"),
                            label = { Text("${lang.displayName} (${lang.nativeName})") }
                        )
                    }
                }
            }
        }

        item {
            JarvisPhaseNoticeBanner(
                title = "Gemini Backend Not Yet Provisioned",
                description = "Phase 1 provides the responsive multilingual chat layout. Secure Gemini integration, streaming responses, and conversation history will be connected in Phase 3. No simulated AI responses are generated.",
                phaseLabel = "Phase 3 Setup",
                icon = Icons.Outlined.AutoAwesome
            )
        }

        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                Text("Prompt Starters", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(8.dp))
                val starters = listOf(
                    "Explain quantum encryption in simple terms",
                    "Translate product launch notes to Hindi & Marathi",
                    "Architect a Kotlin Clean Architecture repository",
                    "Summarize uploaded research papers"
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    starters.forEachIndexed { idx, prompt ->
                        OutlinedButton(
                            onClick = { onDraftChange(prompt) },
                            modifier = Modifier.testTag("ai_starter_$idx")
                        ) {
                            Text(prompt, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))
                OutlinedTextField(
                    value = uiState.aiChatDraft,
                    onValueChange = onDraftChange,
                    label = { Text("Message JARVIS AI (${uiState.selectedLanguage.displayName})") },
                    minLines = 3,
                    maxLines = 6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("ai_chat_composer_input")
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onOpenVoiceAssistant,
                        modifier = Modifier.testTag("ai_to_voice_button")
                    ) {
                        Icon(Icons.Filled.Mic, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Voice Input")
                    }
                    Button(
                        onClick = {
                            submittedNote = if (uiState.aiChatDraft.isBlank()) {
                                "Enter a prompt first."
                            } else {
                                "Prompt staged for ${uiState.selectedLanguage.displayName}. Gemini backend connects in Phase 3."
                            }
                        },
                        modifier = Modifier.testTag("ai_send_prompt_button")
                    ) {
                        Text("Stage Prompt")
                        Spacer(Modifier.width(6.dp))
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                }
                if (submittedNote != null) {
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = submittedNote!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = ext.cyanAccent
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VoiceAssistantScreen(
    contentPadding: PaddingValues,
    uiState: JarvisUiState,
    onOrbStateChange: (OrbActivityState) -> Unit,
    onSpeechSpeedChange: (Float) -> Unit,
    onVoiceProfileChange: (String) -> Unit,
    onOpenPermissions: () -> Unit
) {
    val ext = LocalJarvisColors.current
    val voices = listOf("Aurora Neural (EN/HI)", "Veda Studio (HI/MR)", "Kaito Core (JA/KO)", "Orion Deep (EN)")

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "Advanced Voice Assistant",
                subtitle = "Speech-to-Text, TTS, Hindi/English voice profiles & live audio waveform",
                trailing = { JarvisStatusPill(text = uiState.orbState.label, accentColor = ext.cyanAccent) }
            )
        }

        item {
            JarvisGlassCard(
                modifier = Modifier.fillMaxWidth(),
                highlightBorder = true
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    JarvisAnimatedOrb(orbState = uiState.orbState, orbSize = 152.dp)
                    JarvisWaveform(orbState = uiState.orbState, height = 52.dp)
                    Text(
                        text = uiState.orbState.statusText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = ext.cyanAccent
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val next = if (uiState.orbState == OrbActivityState.LISTENING) OrbActivityState.IDLE else OrbActivityState.LISTENING
                                onOrbStateChange(next)
                            },
                            modifier = Modifier.testTag("voice_mic_toggle_button")
                        ) {
                            Icon(
                                imageVector = if (uiState.orbState == OrbActivityState.LISTENING) Icons.Filled.MicOff else Icons.Filled.Mic,
                                contentDescription = "Toggle microphone preview"
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(if (uiState.orbState == OrbActivityState.LISTENING) "Mic OFF" else "Mic Preview")
                        }
                        OutlinedButton(
                            onClick = { onOrbStateChange(OrbActivityState.THINKING) },
                            modifier = Modifier.testTag("voice_thinking_preview_button")
                        ) { Text("Thinking") }
                        OutlinedButton(
                            onClick = { onOrbStateChange(OrbActivityState.SPEAKING) },
                            modifier = Modifier.testTag("voice_speaking_preview_button")
                        ) { Text("Speaking") }
                        OutlinedButton(
                            onClick = { onOrbStateChange(OrbActivityState.IDLE) },
                            modifier = Modifier.testTag("voice_stop_button")
                        ) {
                            Icon(Icons.Filled.Stop, contentDescription = "Stop speaking", modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Stop")
                        }
                    }
                }
            }
        }

        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("Voice Profile & Speed Settings", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    voices.forEach { voice ->
                        FilterChip(
                            selected = uiState.selectedVoiceProfile == voice,
                            onClick = { onVoiceProfileChange(voice) },
                            label = { Text(voice) }
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Speech Speed: ${String.format("%.2fx", uiState.speechSpeed)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Slider(
                    value = uiState.speechSpeed,
                    onValueChange = onSpeechSpeedChange,
                    valueRange = 0.6f..1.6f,
                    modifier = Modifier.testTag("voice_speed_slider")
                )
                OutlinedButton(
                    onClick = onOpenPermissions,
                    modifier = Modifier.testTag("voice_check_mic_permission_button")
                ) {
                    Text("Check Android Microphone Permission")
                }
            }
        }

        item {
            JarvisPhaseNoticeBanner(
                title = "Microphone Consent & Phase 3 Speech Engine",
                description = "JARVIS AI never activates the microphone without explicit permission. Live STT/TTS audio capture & Gemini voice pipeline connect in Phase 3.",
                phaseLabel = "Phase 3 Voice"
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LiveSearchScreen(
    contentPadding: PaddingValues,
    uiState: JarvisUiState,
    onQueryChange: (String) -> Unit
) {
    val ext = LocalJarvisColors.current
    var selectedScope by remember { mutableStateOf("Web & Tech") }
    val scopes = listOf("Web & Tech", "Latest News", "Research", "Site Summary", "Compare Sources")

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "Live Web Search",
                subtitle = "Grounded real-time news, source links, publication dates & comparisons",
                trailing = { JarvisStatusPill(text = "Phase 4 Grounding", accentColor = ext.warning) }
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.searchQueryDraft,
                        onValueChange = onQueryChange,
                        placeholder = { Text("Search current news, technology updates, or URLs...") },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("live_search_input")
                    )
                    IconButton(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier.minimumInteractiveComponentSize()
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Search requires Phase 4 provider")
                    }
                }
                Spacer(Modifier.height(10.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    scopes.forEach { scope ->
                        FilterChip(
                            selected = selectedScope == scope,
                            onClick = { selectedScope = scope },
                            label = { Text(scope) }
                        )
                    }
                }
            }
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "No Invented Search Results",
                description = "Selected mode: $selectedScope. Real search grounding with verified source titles, URLs, and publication dates connects in Phase 4.",
                phaseLabel = "Phase 4 Search",
                icon = Icons.Outlined.Public
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KnowledgeCenterScreen(
    contentPadding: PaddingValues,
    uiState: JarvisUiState,
    onMemoryToggle: (Boolean) -> Unit,
    onPrivateModeToggle: (Boolean) -> Unit
) {
    val ext = LocalJarvisColors.current
    var searchNote by remember { mutableStateOf("") }
    var selectedFolder by remember { mutableStateOf("All Folders") }
    val folders = listOf("All Folders", "Research", "Code Specs", "Personal Notes", "Uploaded Docs")

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "Personal Knowledge Center",
                subtitle = "Folders, document Q&A (PDF, TXT, DOCX, CSV), and user-approved memory",
                trailing = { JarvisStatusPill(text = "Phase 4 Vault", accentColor = ext.cyanAccent) }
            )
        }

        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = searchNote,
                    onValueChange = { searchNote = it },
                    placeholder = { Text("Search saved notes, documents, or past conversations...") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search knowledge") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("knowledge_search_input")
                )
                Spacer(Modifier.height(10.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    folders.forEach { folder ->
                        FilterChip(
                            selected = selectedFolder == folder,
                            onClick = { selectedFolder = folder },
                            leadingIcon = { Icon(Icons.Outlined.Folder, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            label = { Text(folder) }
                        )
                    }
                }
            }
        }

        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                Text("User-Controlled Memory & Privacy", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("User-Approved Long-Term Memory", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = "Only store facts you explicitly approve, edit, or delete.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = uiState.memoryEnabled,
                        onCheckedChange = onMemoryToggle,
                        modifier = Modifier.testTag("knowledge_memory_switch")
                    )
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Private Conversation Mode", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = "Prevent sessions from writing into personal memory.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = uiState.privateModeEnabled,
                        onCheckedChange = onPrivateModeToggle,
                        modifier = Modifier.testTag("knowledge_private_mode_switch")
                    )
                }
            }
        }

        item {
            JarvisPhaseNoticeBanner(
                title = "Untrusted Document Isolation & Phase 4 Storage",
                description = "Uploaded PDF, TXT, DOCX, and CSV files will be treated strictly as untrusted data—never system instructions. File ingestion & encrypted storage launch in Phase 2/4.",
                phaseLabel = "Phase 4 Ready",
                icon = Icons.Outlined.Lock
            )
        }

        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Outlined.UploadFile, contentDescription = "Document upload", tint = ext.cyanAccent)
                    Column(modifier = Modifier.weight(1f)) {
                        Text("No Saved Knowledge Items Yet", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = "Folder: $selectedFolder • Supported formats: PDF, TXT, DOCX, CSV.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenScaffoldContainer(
    contentPadding: PaddingValues,
    content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit
) {
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
            verticalArrangement = Arrangement.spacedBy(14.dp),
            content = content
        )
    }
}
