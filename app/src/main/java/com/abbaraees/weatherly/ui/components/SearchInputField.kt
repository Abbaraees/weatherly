package com.abbaraees.weatherly.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.abbaraees.weatherly.ui.theme.WeatherlySkyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInputField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: (KeyboardActionScope.() -> Unit),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value ,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = "Enter city name")
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = WeatherlySkyBlue,
                focusedTrailingIconColor = WeatherlySkyBlue,

            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = onSearch
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchInputField() {
    SearchInputField(value = "", {}, {})
}