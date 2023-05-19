package com.dmitriivoronko.chartsta.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class NavCommandHandler : NavCommandDispatcher, NavCommandProvider {

    private val _navCommands = MutableSharedFlow<NavCommand?>()

    override val navCommands: SharedFlow<NavCommand?>
        get() = _navCommands

    override suspend fun dispatch(
        navCommand: NavCommand.NavControllerCommand,
    ) {
        _navCommands.emit(navCommand)
    }
}

sealed interface NavCommand {

    fun interface NavControllerCommand : NavCommand {

        suspend operator fun invoke(navController: NavController)
    }
}

interface NavCommandDispatcher {

    /**
     * Dispatch navigation command to command queue
     *
     * @param navCommand Navigation command to dispatch
     */
    suspend fun dispatch(
        navCommand: NavCommand.NavControllerCommand,
    )
}

interface NavCommandProvider {

    val navCommands: SharedFlow<NavCommand?>
}

@Composable
fun NavCommandProvider.Bind(navController: NavController) {
    LaunchedEffect(navController) {
        navCommands
            .collect { navCommand ->
                when (navCommand) {
                    is NavCommand.NavControllerCommand -> {
                        navCommand(navController)
                    }

                    null                               -> {
                        //Do nothing
                    }
                }
            }
    }
}