package com.example.loginlogout.presentation.screen.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.loginlogout.navigation.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    loginScreenViewModel: LoginScreenViewModel = viewModel()
){

    val context = LocalContext.current
    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = "Login Screen",
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )
            if(showLoginForm.value) UserForm(loading = false, isCreateAccount = false){ email, password ->
                loginScreenViewModel.signInWithEmailAndPassword(email, password){
                    navController.navigate(Screen.Home.route)
                }
            }
            else {
                UserForm(loading = false, isCreateAccount = true){ email, password ->
                    if(email.contains('@') && email.contains(".com")){
                        if(password.count() >= 6) {
                            loginScreenViewModel.createUserWithEmailAndPassword(email, password) {
                                navController.navigate(Screen.Home.route)
                            }
                        }
                        else {
                            Toast.makeText(context,"Password must contain at least 6 characters", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(context,"Please enter a valid email", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text = if(showLoginForm.value) "Sign Up" else "Login"
            Text(text = if(showLoginForm.value) "New User?" else "Already Have an Account?")
            Text(
                text = text,
                modifier = Modifier
                    .clickable {
                        showLoginForm.value = !showLoginForm.value
                    }
                    .padding(start = 5.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.secondaryVariant)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) { email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty() }


    Column(
        modifier = Modifier
            .height(250.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(isCreateAccount) Text(text = "Please enter a valid email and password", modifier = Modifier.padding(4.dp)) else Text(text = "")
        EmailInput(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            }
        )

        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            label = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if(!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            },
        )

        SubmitButton(
            text = if(isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ){
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(text: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = text, modifier = Modifier.padding(5.dp))

    }

}

@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    label: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    val visualTransformation = if(passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = { Text(text = label)},
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp),
        modifier = modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = {PasswordVisibility(passwordVisibility = passwordVisibility)},
        keyboardActions = onAction
    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visibile = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visibile}) {
        Icons.Default.Close
    }
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    label: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        value = emailState,
        label = label,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction
    )
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: MutableState<String>,
    label: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = {value.value = it},
        label = {Text(text = label)},
        singleLine = isSingleLine,
        enabled = enabled,
        textStyle = TextStyle(fontSize = 18.sp),
        modifier = modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction
    )

}

