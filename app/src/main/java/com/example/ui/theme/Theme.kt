package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Immutable
data class JarvisExtendedColors(
    val glassSurface: Color,
    val glassSurfaceStrong: Color,
    val glassBorderStart: Color,
    val glassBorderEnd: Color,
    val cyanAccent: Color,
    val purpleAccent: Color,
    val electricAccent: Color,
    val success: Color,
    val warning: Color,
    val danger: Color,
    val codeBackground: Color,
    val orbOuterRing: Color,
    val orbCore: Color,
    val heroScrim: Color,
    val isDark: Boolean
)

private val DarkExtendedColors = JarvisExtendedColors(
    glassSurface = Color(0xCC0F192E),
    glassSurfaceStrong = Color(0xE615223D),
    glassBorderStart = Color(0x6600E5FF),
    glassBorderEnd = Color(0x448C52FF),
    cyanAccent = CyanGlow,
    purpleAccent = QuantumPurple,
    electricAccent = ElectricBlue,
    success = StatusEmerald,
    warning = StatusAmber,
    danger = StatusCoral,
    codeBackground = Color(0xFF070C17),
    orbOuterRing = CyanGlow,
    orbCore = ElectricBlueBright,
    heroScrim = Color(0xD904070E),
    isDark = true
)

private val LightExtendedColors = JarvisExtendedColors(
    glassSurface = Color(0xE6FFFFFF),
    glassSurfaceStrong = Color(0xFFF5F8FE),
    glassBorderStart = Color(0x66008394),
    glassBorderEnd = Color(0x445E35B1),
    cyanAccent = TealCyanAccent,
    purpleAccent = IndigoAccent,
    electricAccent = CobaltPrimary,
    success = StatusEmeraldDark,
    warning = StatusAmberDark,
    danger = StatusCoralDark,
    codeBackground = Color(0xFFEEF3FB),
    orbOuterRing = TealCyanAccent,
    orbCore = CobaltPrimary,
    heroScrim = Color(0xCC0B1324),
    isDark = false
)

val LocalJarvisColors = staticCompositionLocalOf { DarkExtendedColors }

private val JarvisDarkColorScheme = darkColorScheme(
    primary = CyanGlow,
    onPrimary = DeepSpaceBlack,
    primaryContainer = Color(0xFF0A2E4D),
    onPrimaryContainer = TextPrimaryDark,
    secondary = ElectricBlueBright,
    onSecondary = DeepSpaceBlack,
    secondaryContainer = NavySurfaceElevated,
    onSecondaryContainer = TextPrimaryDark,
    tertiary = QuantumPurpleSoft,
    onTertiary = DeepSpaceBlack,
    tertiaryContainer = Color(0xFF281A4D),
    onTertiaryContainer = TextPrimaryDark,
    background = DeepSpaceBlack,
    onBackground = TextPrimaryDark,
    surface = MidnightNavy,
    onSurface = TextPrimaryDark,
    surfaceVariant = NavySurface,
    onSurfaceVariant = TextSecondaryDark,
    outline = NavyBorder,
    outlineVariant = Color(0xFF152440),
    error = StatusCoral,
    onError = DeepSpaceBlack
)

private val JarvisLightColorScheme = lightColorScheme(
    primary = CobaltPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6E4FF),
    onPrimaryContainer = TextPrimaryLight,
    secondary = TealCyanAccent,
    onSecondary = Color.White,
    secondaryContainer = IceSurfaceElevated,
    onSecondaryContainer = TextPrimaryLight,
    tertiary = IndigoAccent,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFE7DEFF),
    onTertiaryContainer = TextPrimaryLight,
    background = IceBackground,
    onBackground = TextPrimaryLight,
    surface = IceSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = IceSurfaceElevated,
    onSurfaceVariant = TextSecondaryLight,
    outline = IceBorder,
    outlineVariant = Color(0xFFDCE6F4),
    error = StatusCoralDark,
    onError = Color.White
)

val JarvisShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun JarvisAiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> JarvisDarkColorScheme
        else -> JarvisLightColorScheme
    }

    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    CompositionLocalProvider(LocalJarvisColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = JarvisTypography,
            shapes = JarvisShapes,
            content = content
        )
    }
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    JarvisAiTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
