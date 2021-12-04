package com.metehanbolat.jetpackcomposedetailsrelearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.rememberCoilPainter
import com.metehanbolat.jetpackcomposedetailsrelearn.ui.theme.JetpackComposeDetailsRelearnTheme
import com.metehanbolat.jetpackcomposedetailsrelearn.ui.theme.LightGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeDetailsRelearnTheme {
                UsersApplication()
            }
        }
    }
}

@Composable
fun UsersApplication(userProfiles: List<UserProfile> = userProfileList) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "users_list") {
        composable("users_list"){
            UserListScreen(userProfiles, navController)
        }
        composable("user_details"){
            UserProfileDetailsScreen()
        }
    }
}

@Composable
fun UserListScreen(userProfiles: List<UserProfile>, navController: NavHostController?) {
    Scaffold(
        topBar = { AppBar() }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ){
            LazyColumn {
                items(userProfiles){ userProfile ->
                    ProfileCard(userProfile = userProfile) {
                        navController?.navigate("user_details")
                    }
                }
            }
        }
    }
}

@Composable
fun UserProfileDetailsScreen(userProfile: UserProfile = userProfileList[0]) {
    Scaffold(topBar = { AppBar() }) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ProfilePicture(userProfile.pictureUrl, userProfile.status, 240.dp)
                ProfileContent(userProfile.name, userProfile.status, Alignment.CenterHorizontally)
            }
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home Icon",
                modifier = Modifier.padding(horizontal = 12.dp)
            )},
        title = { Text(text = "Messaging Application users") }
    )
}

@Composable
fun ProfileCard(userProfile: UserProfile, clickAction: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clickable { clickAction() },
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfilePicture(userProfile.pictureUrl, userProfile.status, 72.dp)
            ProfileContent(userProfile.name, userProfile.status, Alignment.Start)
        }
    }
}

@Composable
fun ProfilePicture(pictureUrl: String, onlineStatus: Boolean, imageSize: Dp) {
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 2.dp,
            color = if (onlineStatus) LightGreen else Color.Red
        ),
        modifier = Modifier.padding(16.dp),
        elevation = 4.dp
    ) {
        Image(
            painter = rememberCoilPainter(
                request = pictureUrl,
                requestBuilder = {
                    transformations(CircleCropTransformation())
                }
            ),
            modifier = Modifier.size(imageSize),
            contentDescription = "Profile Picture"
        )
    }
}

@Composable
fun ProfileContent(userName: String, onlineStatus: Boolean, horizontalAlignment: Alignment.Horizontal) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = userName,
            style = MaterialTheme.typography.h5
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = if (onlineStatus) "Active Now" else "Offline",
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileDetailsPreview() {
    JetpackComposeDetailsRelearnTheme {
        UserProfileDetailsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    JetpackComposeDetailsRelearnTheme {
        UserListScreen(userProfileList, null)
    }
}