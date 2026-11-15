package com.example.ui.screens

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
import androidx.compose.material.icons.outlined.Brush
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.MovieCreation
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material.icons.outlined.ViewInAr
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.components.JarvisGlassCard
import com.example.ui.components.JarvisPhaseNoticeBanner
import com.example.ui.components.JarvisSectionHeader
import com.example.ui.components.JarvisStatusPill
import com.example.ui.theme.LocalJarvisColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImageStudioScreen(contentPadding: PaddingValues) {
    val ext = LocalJarvisColors.current
    var mode by remember { mutableStateOf("Text-to-Image") }
    var style by remember { mutableStateOf("Anime Illustration") }
    var prompt by remember { mutableStateOf("") }

    val styles = listOf(
        "Anime Illustration", "Realistic Artwork", "Pencil Sketch",
        "Digital Painting", "Fantasy Scene", "Portrait", "Character Sheet", "Background Art"
    )

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "AI Image Generation Studio",
                subtitle = "Text-to-image, image-to-image, reference upload, variations & PNG/JPG export",
                trailing = { JarvisStatusPill(text = "Phase 7 Image", accentColor = ext.warning) }
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Text-to-Image", "Image-to-Image", "Edit & Inpaint", "Variations").forEach { m ->
                        FilterChip(
                            selected = mode == m,
                            onClick = { mode = m },
                            label = { Text(m) }
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = prompt,
                    onValueChange = { prompt = it },
                    label = { Text("Image Prompt ($mode)") },
                    minLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("image_studio_prompt")
                )
                Spacer(Modifier.height(10.dp))
                Text("Art Style", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    styles.forEach { s ->
                        FilterChip(
                            selected = style == s,
                            onClick = { style = s },
                            label = { Text(s) }
                        )
                    }
                }
            }
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "Image Gallery Empty • Real Backend API Pending Phase 7",
                description = "Active style: $style ($mode). Placeholder images are never displayed as generated results. Real image generation connects in Phase 7.",
                phaseLabel = "Phase 7 API",
                icon = Icons.Outlined.Image
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeStudioScreen(
    contentPadding: PaddingValues,
    onOpenSeriesCreator: () -> Unit
) {
    val ext = LocalJarvisColors.current
    val animeStyles = listOf(
        "Realistic Graphite Pencil Anime",
        "Ultra-Detailed Pencil Drawing",
        "Black and White Manga",
        "Dark Anime Portrait",
        "Anime Girl Portrait",
        "Anime Boy Portrait",
        "Long Flowing Hair Artwork",
        "Detailed Anime Eyes",
        "Multi-Eye Fantasy Characters",
        "Nine-Character Anime Collage",
        "Full-Body Anime Artwork",
        "Gothic Architecture Anime Scene",
        "Fantasy Anime Illustration",
        "Character Concept Art",
        "Hand-Drawn Sketchbook Effect",
        "Cinematic Anime Poster"
    )
    var selectedStyle by remember { mutableStateOf(animeStyles.first()) }
    var characterIdea by remember { mutableStateOf("") }
    var hairStyle by remember { mutableStateOf("Long Flowing") }
    var eyeDesign by remember { mutableStateOf("High-Detail Shimmer") }
    var lighting by remember { mutableStateOf("Dramatic Rim + Cross-Hatch") }

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "Advanced Anime Drawing Studio",
                subtitle = "16 anime & graphite pencil styles, character consistency & prompt assistant",
                trailing = { JarvisStatusPill(text = "Phase 7 Anime", accentColor = ext.purpleAccent) }
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                Text("16 Dedicated Anime & Graphite Art Styles", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    animeStyles.forEachIndexed { index, artStyle ->
                        FilterChip(
                            selected = selectedStyle == artStyle,
                            onClick = { selectedStyle = artStyle },
                            modifier = Modifier.testTag("anime_style_$index"),
                            label = { Text(artStyle, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
            }
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("Character Customization & Graphite Quality", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = characterIdea,
                    onValueChange = { characterIdea = it },
                    label = { Text("Simple Character Idea (Prompt Assistant)") },
                    placeholder = { Text("e.g., Original silver-haired mage standing in gothic cathedral rain...") },
                    minLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("anime_prompt_assistant_input")
                )
                Spacer(Modifier.height(10.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("Long Flowing", "Twin Braids", "Spiky Layered", "Short Bob").forEach { hair ->
                        FilterChip(
                            selected = hairStyle == hair,
                            onClick = { hairStyle = hair },
                            label = { Text("Hair: $hair") }
                        )
                    }
                }
                Spacer(Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("High-Detail Shimmer", "Multi-Eye Fantasy", "Sharp Manga Ink").forEach { eye ->
                        FilterChip(
                            selected = eyeDesign == eye,
                            onClick = { eyeDesign = eye },
                            label = { Text("Eyes: $eye") }
                        )
                    }
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Graphite Engine Preset: Fine pencil strokes • Natural proportions • Smooth shading • Paper grain • $lighting",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = onOpenSeriesCreator,
                    modifier = Modifier.testTag("open_anime_series_creator_button")
                ) {
                    Icon(Icons.Outlined.MovieCreation, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Open Anime Movie & Series Creator")
                }
            }
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "Original Character Artwork • Phase 7 Generation",
                description = "Selected style: $selectedStyle ($hairStyle, $eyeDesign). High-detail anime generation & before/after preview connect in Phase 7.",
                phaseLabel = "Phase 7 Anime",
                icon = Icons.Outlined.Brush
            )
        }
    }
}

@Composable
fun AnimeSeriesCreatorScreen(contentPadding: PaddingValues) {
    val ext = LocalJarvisColors.current
    var seriesTitle by remember { mutableStateOf("") }
    var activeSection by remember { mutableStateOf("Story") }

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "Anime Movie & Series Creator",
                subtitle = "Original stories, character consistency profiles, episodes, dialogue & timeline",
                trailing = { JarvisStatusPill(text = "Phase 8 Series", accentColor = ext.purpleAccent) }
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                OutlinedTextField(
                    value = seriesTitle,
                    onValueChange = { seriesTitle = it },
                    label = { Text("Original Anime Series / Movie Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Story", "Characters", "Episodes", "Storyboard").forEach { tab ->
                        FilterChip(
                            selected = activeSection == tab,
                            onClick = { activeSection = tab },
                            label = { Text(tab) }
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "$activeSection workspace staged. Full story generator, dialogue synthesis, and production timeline connect in Phase 8.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VideoStudioScreen(
    contentPadding: PaddingValues,
    onOpenSeriesCreator: () -> Unit
) {
    val ext = LocalJarvisColors.current
    var mode by remember { mutableStateOf("Text-to-Video") }
    var scenePrompt by remember { mutableStateOf("") }

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "AI Video Studio",
                subtitle = "Text-to-video, image-to-video, storyboard, captions, voice-over & MP4 export",
                trailing = { JarvisStatusPill(text = "Phase 8 Video", accentColor = ext.warning) }
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Text-to-Video", "Image-to-Video", "Storyboard", "Timeline & Subtitles").forEach { m ->
                        FilterChip(
                            selected = mode == m,
                            onClick = { mode = m },
                            label = { Text(m) }
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = scenePrompt,
                    onValueChange = { scenePrompt = it },
                    label = { Text("Scene Description ($mode)") },
                    minLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("video_studio_prompt")
                )
                Spacer(Modifier.height(10.dp))
                OutlinedButton(
                    onClick = onOpenSeriesCreator,
                    modifier = Modifier.testTag("video_to_series_button")
                ) {
                    Icon(Icons.Outlined.MovieCreation, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Anime Movie & Series Production")
                }
            }
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "Honest Render Status • Phase 8 Video Pipeline",
                description = "Active tab: $mode. A storyboard or preview is never labeled as a rendered MP4. Cloud video rendering & export connect in Phase 8.",
                phaseLabel = "Phase 8 Render",
                icon = Icons.Outlined.Videocam
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CgiStudioScreen(contentPadding: PaddingValues) {
    val ext = LocalJarvisColors.current
    var category by remember { mutableStateOf("3D Character Concepts") }
    var exportFormat by remember { mutableStateOf(".glTF / .GLB") }

    ScreenScaffoldContainer(contentPadding = contentPadding) {
        item {
            JarvisSectionHeader(
                title = "3D & CGI Design Studio",
                subtitle = "Character concepts, environments, lighting/camera rigs, VFX & 3D export",
                trailing = { JarvisStatusPill(text = "Phase 8 3D/CGI", accentColor = ext.warning) }
            )
        }
        item {
            JarvisGlassCard(modifier = Modifier.fillMaxWidth(), highlightBorder = true) {
                Text("Concept & Planning Mode", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(
                        "3D Character Concepts",
                        "Environment Design",
                        "Product Concepts",
                        "Architecture",
                        "Lighting & Camera",
                        "VFX & Animation"
                    ).forEach { c ->
                        FilterChip(
                            selected = category == c,
                            onClick = { category = c },
                            label = { Text(c) }
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                Text("Target 3D Interchange Format", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(6.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(".glTF / .GLB", ".OBJ", ".FBX", ".USDZ").forEach { fmt ->
                        FilterChip(
                            selected = exportFormat == fmt,
                            onClick = { exportFormat = fmt },
                            label = { Text(fmt) }
                        )
                    }
                }
            }
        }
        item {
            JarvisPhaseNoticeBanner(
                title = "Concept Renders vs Editable 3D Meshes",
                description = "Mode: $category • Target: $exportFormat. JARVIS AI clearly distinguishes 2D concept renders from real editable 3D geometry pipelines (Phase 8).",
                phaseLabel = "Phase 8 CGI",
                icon = Icons.Outlined.ViewInAr
            )
        }
    }
}
