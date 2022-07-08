package com.example.loginlogout.domain.model

sealed class OnBoardingPage(
    val title: String,
    val description: String
){
    object First: OnBoardingPage(
        title = "Lorem Ipsum",
        description = "Ut eget gravida leo. Nam nec metus a diam gravida pellentesque. Maecenas id sodales turpis."
    )
    object Second: OnBoardingPage(
        title = "Pellentesque",
        description = "Quisque a magna sit amet felis placerat condimentum. Etiam pellentesque magna eros, cursus dignissim libero feugiat non."
    )
    object Third: OnBoardingPage(
        title = "Mauris Auctor Finibus",
        description = "Suspendisse tempus nisi magna, eu maximus nisl dapibus pretium. Fusce turpis nibh, pulvinar nec ipsum sed, rhoncus fringilla ante. Mauris quam enim, sagittis et eros at, dapibus maximus enim. Etiam porttitor imperdiet dolor, vitae convallis erat suscipit et. Sed tincidunt blandit pulvinar."
    )
}
