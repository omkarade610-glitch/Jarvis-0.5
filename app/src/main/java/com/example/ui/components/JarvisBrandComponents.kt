package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.state.OrbActivityState
import com.example.ui.theme.LocalJarvisColors
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun JarvisLogoMark(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp
) {
    val ext = LocalJarvisColors.current
    val primary = ext.cyanAccent
    val secondary = ext.electricAccent
    val tertiary = ext.purpleAccent

    Canvas(
        modifier = modifier
            .size(size)
            .semantics { contentDescription = "JARVIS AI emblem" }
    ) {
        val w = this.size.width
        val h = this.size.height
        val center = Offset(w / 2f, h / 2f)
        val radius = minOf(w, h) * 0.44f

        // Outer hexagonal orbital ring
        val hexPath = Path()
        for (i in 0 until 6) {
            val angle = Math.toRadians((60.0 * i) - 30.0)
            val px = center.x + (radius * cos(angle)).toFloat()
            val py = center.y + (radius * sin(angle)).toFloat()
            if (i == 0) hexPath.moveTo(px, py) else hexPath.lineTo(px, py)
        }
        hexPath.close()

        drawPath(
            path = hexPath,
            brush = Brush.linearGradient(listOf(primary, secondary, tertiary)),
            style = Stroke(width = w * 0.065f)
        )

        // Inner circular arc
        drawCircle(
            color = primary.copy(alpha = 0.5f),
            radius = radius * 0.62f,
            center = center,
            style = Stroke(width = w * 0.04f)
        )

        // Core diamond
        val coreR = radius * 0.34f
        val diamond = Path().apply {
            moveTo(center.x, center.y - coreR)
            lineTo(center.x + coreR, center.y)
            lineTo(center.x, center.y + coreR)
            lineTo(center.x - coreR, center.y)
            close()
        }
        drawPath(
            path = diamond,
            brush = Brush.radialGradient(
                colors = listOf(Color.White, primary, secondary),
                center = center,
                radius = coreR * 1.4f
            )
        )
    }
}

@Composable
fun JarvisAnimatedOrb(
    orbState: OrbActivityState,
    modifier: Modifier = Modifier,
    orbSize: Dp = 148.dp
) {
    val ext = LocalJarvisColors.current
    val infiniteTransition = rememberInfiniteTransition(label = "jarvis_orb_anim")

    val speedMultiplier = when (orbState) {
        OrbActivityState.IDLE -> 1f
        OrbActivityState.LISTENING -> 2.0f
        OrbActivityState.THINKING -> 2.8f
        OrbActivityState.SPEAKING -> 1.7f
    }

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (9000 / speedMultiplier).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orb_rotation"
    )

    val counterRotation by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (6500 / speedMultiplier).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orb_counter_rotation"
    )

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.88f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (1800 / speedMultiplier).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb_pulse"
    )

    val ringPrimary = when (orbState) {
        OrbActivityState.IDLE -> ext.cyanAccent
        OrbActivityState.LISTENING -> ext.success
        OrbActivityState.THINKING -> ext.purpleAccent
        OrbActivityState.SPEAKING -> ext.electricAccent
    }

    val ringSecondary = when (orbState) {
        OrbActivityState.IDLE -> ext.electricAccent
        OrbActivityState.LISTENING -> ext.cyanAccent
        OrbActivityState.THINKING -> ext.cyanAccent
        OrbActivityState.SPEAKING -> ext.purpleAccent
    }

    Canvas(
        modifier = modifier
            .size(orbSize)
            .semantics { contentDescription = "JARVIS AI animated orb in ${orbState.label} mode" }
    ) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val baseRadius = minOf(size.width, size.height) * 0.34f

        // Ambient radial aura
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    ringPrimary.copy(alpha = 0.34f * pulse),
                    ringSecondary.copy(alpha = 0.14f),
                    Color.Transparent
                ),
                center = center,
                radius = baseRadius * 1.55f * pulse
            ),
            radius = baseRadius * 1.55f * pulse,
            center = center
        )

        // Outer rotating segmented arc
        rotate(degrees = rotation, pivot = center) {
            val arcRadius = baseRadius * 1.22f
            val arcTopLeft = Offset(center.x - arcRadius, center.y - arcRadius)
            val arcSize = Size(arcRadius * 2f, arcRadius * 2f)
            drawArc(
                color = ringPrimary.copy(alpha = 0.85f),
                startAngle = 10f,
                sweepAngle = 110f,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = 3.2.dp.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = ringSecondary.copy(alpha = 0.7f),
                startAngle = 190f,
                sweepAngle = 95f,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = 2.4.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        // Inner counter-rotating orbital ring
        rotate(degrees = counterRotation, pivot = center) {
            val innerR = baseRadius * 0.98f
            val innerTopLeft = Offset(center.x - innerR, center.y - innerR)
            val innerSize = Size(innerR * 2f, innerR * 2f)
            drawArc(
                color = ringSecondary.copy(alpha = 0.9f),
                startAngle = 45f,
                sweepAngle = 135f,
                useCenter = false,
                topLeft = innerTopLeft,
                size = innerSize,
                style = Stroke(width = 2.2.dp.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = ringPrimary.copy(alpha = 0.5f),
                startAngle = 235f,
                sweepAngle = 70f,
                useCenter = false,
                topLeft = innerTopLeft,
                size = innerSize,
                style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        // Core holographic sphere
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.95f),
                    ringPrimary.copy(alpha = 0.88f),
                    ringSecondary.copy(alpha = 0.72f),
                    ext.purpleAccent.copy(alpha = 0.35f)
                ),
                center = Offset(center.x - baseRadius * 0.18f, center.y - baseRadius * 0.18f),
                radius = baseRadius * 0.9f
            ),
            radius = baseRadius * 0.72f * (0.94f + (pulse - 0.88f) * 0.35f),
            center = center
        )
    }
}

@Composable
fun JarvisWaveform(
    orbState: OrbActivityState,
    modifier: Modifier = Modifier,
    height: Dp = 44.dp
) {
    val ext = LocalJarvisColors.current
    val infiniteTransition = rememberInfiniteTransition(label = "jarvis_waveform")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2f * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = when (orbState) {
                    OrbActivityState.IDLE -> 2600
                    OrbActivityState.LISTENING -> 1100
                    OrbActivityState.THINKING -> 900
                    OrbActivityState.SPEAKING -> 1000
                },
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "waveform_phase"
    )

    val amplitudeFactor = when (orbState) {
        OrbActivityState.IDLE -> 0.25f
        OrbActivityState.LISTENING -> 0.85f
        OrbActivityState.THINKING -> 0.55f
        OrbActivityState.SPEAKING -> 0.95f
    }

    val barColor = when (orbState) {
        OrbActivityState.IDLE -> ext.cyanAccent
        OrbActivityState.LISTENING -> ext.success
        OrbActivityState.THINKING -> ext.purpleAccent
        OrbActivityState.SPEAKING -> ext.electricAccent
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .semantics { contentDescription = "Futuristic audio waveform visualization" }
    ) {
        val barCount = 32
        val totalWidth = size.width
        val step = totalWidth / barCount
        val centerY = size.height / 2f
        val maxHalfHeight = size.height * 0.44f

        for (i in 0 until barCount) {
            val norm = i.toFloat() / barCount
            val envelope = sin(norm * PI).toFloat()
            val wave = kotlin.math.abs(sin(phase + i * 0.48f))
            val halfH = (maxHalfHeight * (0.14f + 0.86f * wave * amplitudeFactor) * (0.35f + 0.65f * envelope))
            val x = step * i + step / 2f
            drawLine(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        barColor.copy(alpha = 0.45f),
                        barColor,
                        ext.purpleAccent.copy(alpha = 0.65f)
                    )
                ),
                start = Offset(x, centerY - halfH),
                end = Offset(x, centerY + halfH),
                strokeWidth = (step * 0.46f).coerceIn(2.dp.toPx(), 5.dp.toPx()),
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun JarvisGlassCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    contentPadding: Dp = 16.dp,
    highlightBorder: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val ext = LocalJarvisColors.current
    val shape = MaterialTheme.shapes.medium
    val borderBrush = Brush.linearGradient(
        colors = if (highlightBorder) {
            listOf(ext.cyanAccent.copy(alpha = 0.75f), ext.purpleAccent.copy(alpha = 0.6f))
        } else {
            listOf(ext.glassBorderStart, ext.glassBorderEnd)
        }
    )

    Surface(
        modifier = modifier
            .clip(shape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .border(width = 1.dp, brush = borderBrush, shape = shape),
        shape = shape,
        color = ext.glassSurface,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            content = content
        )
    }
}

@Composable
fun JarvisStatusPill(
    text: String,
    accentColor: Color,
    modifier: Modifier = Modifier,
    leadingDot: Boolean = true
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(accentColor.copy(alpha = 0.14f))
            .border(BorderStroke(1.dp, accentColor.copy(alpha = 0.45f)), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if (leadingDot) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(accentColor)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = accentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun JarvisPhaseNoticeBanner(
    title: String,
    description: String,
    phaseLabel: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Info
) {
    val ext = LocalJarvisColors.current
    JarvisGlassCard(
        modifier = modifier.fillMaxWidth(),
        highlightBorder = true,
        contentPadding = 14.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ext.cyanAccent.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = ext.cyanAccent,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    JarvisStatusPill(
                        text = phaseLabel,
                        accentColor = ext.warning,
                        leadingDot = false
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun JarvisSectionHeader(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    trailing: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (subtitle != null) {
                Spacer(Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (trailing != null) {
            Spacer(Modifier.width(8.dp))
            trailing()
        }
    }
}
