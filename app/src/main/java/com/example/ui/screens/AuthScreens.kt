package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.components.JarvisAnimatedOrb
import com.example.ui.components.JarvisGlassCard
import com.example.ui.components.JarvisLogoMark
import com.example.ui.components.JarvisPhaseNoticeBanner
import com.example.ui.components.JarvisStatusPill
import com.example.ui.state.AuthFormState
import com.example.ui.state.AuthMode
import com.example.ui.state.OrbActivityState
import com.example.ui.theme.LocalJarvisColors

@Composable
fun AuthScreen(
    contentPadding: PaddingValues,
    authState: AuthFormState,
    onModeChange: (AuthMode) -> Unit,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSubmit: () -> Unit,
    onEnterWorkspacePreview: () -> Unit
) {
    val ext = LocalJarvisColors.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 560.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                JarvisLogoMark(size = 36.dp)
                Text(
                    text = "JARVIS AI",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            JarvisAnimatedOrb(
                orbState = OrbActivityState.IDLE,
                orbSize = 116.dp
            )

            JarvisStatusPill(
                text = "Phase 1 Auth UI • Cloud Auth in Phase 2",
                accentColor = ext.cyanAccent
            )

            // Mode Tabs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                AuthMode.entries.forEach { mode ->
                    FilterChip(
                        selected = authState.mode == mode,
                        onClick = { onModeChange(mode) },
                        modifier = Modifier.testTag("auth_mode_${mode.name.lowercase()}"),
                        label = {
                            Text(
                                text = when (mode) {
                                    AuthMode.SIGN_IN -> "Sign In"
                                    AuthMode.SIGN_UP -> "Sign Up"
                                    AuthMode.FORGOT_PASSWORD -> "Reset"
                                }
                            )
                        }
                    )
                }
            }

            JarvisGlassCard(
                modifier = Modifier.fillMaxWidth(),
                highlightBorder = true,
                contentPadding = 20.dp
            ) {
                Text(
                    text = authState.mode.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = when (authState.mode) {
                        AuthMode.SIGN_IN -> "Sign in to access synced workspaces, AI memory, and private projects."
                        AuthMode.SIGN_UP -> "Register a new JARVIS AI command profile with encrypted user isolation."
                        AuthMode.FORGOT_PASSWORD -> "Verify your account email to prepare a secure password reset request."
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(16.dp))

                if (authState.mode == AuthMode.SIGN_UP) {
                    OutlinedTextField(
                        value = authState.fullName,
                        onValueChange = onFullNameChange,
                        label = { Text("Full Name") },
                        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Full name") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_fullname_input")
                    )
                    Spacer(Modifier.height(12.dp))
                }

                OutlinedTextField(
                    value = authState.email,
                    onValueChange = onEmailChange,
                    label = { Text("Email Address") },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email address") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("auth_email_input")
                )

                if (authState.mode != AuthMode.FORGOT_PASSWORD) {
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = authState.password,
                        onValueChange = onPasswordChange,
                        label = { Text("Password (min 8 chars)") },
                        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
                        trailingIcon = {
                            IconButton(
                                onClick = onTogglePasswordVisibility,
                                modifier = Modifier.minimumInteractiveComponentSize()
                            ) {
                                Icon(
                                    imageVector = if (authState.showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                    contentDescription = if (authState.showPassword) "Hide password" else "Show password"
                                )
                            }
                        },
                        visualTransformation = if (authState.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_password_input")
                    )
                }

                if (authState.mode == AuthMode.SIGN_UP) {
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = authState.confirmPassword,
                        onValueChange = onConfirmPasswordChange,
                        label = { Text("Confirm Password") },
                        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirm password") },
                        visualTransformation = if (authState.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_confirm_password_input")
                    )
                }

                if (authState.validationError != null) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = authState.validationError,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.testTag("auth_validation_error")
                    )
                }

                if (authState.infoBanner != null) {
                    Spacer(Modifier.height(12.dp))
                    JarvisPhaseNoticeBanner(
                        title = "Form Validated • Backend Pending",
                        description = authState.infoBanner,
                        phaseLabel = "Phase 2 Auth",
                        icon = Icons.Outlined.VerifiedUser
                    )
                }

                Spacer(Modifier.height(18.dp))

                Button(
                    onClick = onSubmit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("auth_submit_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ext.electricAccent,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = when (authState.mode) {
                            AuthMode.SIGN_IN -> "Validate & Sign In"
                            AuthMode.SIGN_UP -> "Validate & Create Account"
                            AuthMode.FORGOT_PASSWORD -> "Validate Recovery Email"
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            OutlinedButton(
                onClick = onEnterWorkspacePreview,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("enter_workspace_preview_button")
            ) {
                Icon(
                    imageVector = Icons.Outlined.Explore,
                    contentDescription = "Preview workspace"
                )
                Spacer(Modifier.padding(horizontal = 4.dp))
                Text("Explore Dashboard & 17 Workspaces (Phase 1)")
            }

            Text(
                text = "Security Note: No credentials are stored locally or faked. Real authentication & user-isolated storage will be provisioned in Phase 2.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
