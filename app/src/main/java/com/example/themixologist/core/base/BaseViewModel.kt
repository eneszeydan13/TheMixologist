import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel : ViewModel() {
    // SharedFlow for one-time events (Navigation, Toasts, Snackbars)
    // We use SharedFlow because these shouldn't re-trigger on screen rotation
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    protected suspend fun sendEvent(event: UiEvent) {
        _uiEvent.emit(event)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class Navigate(val direction: Any) : UiEvent() // Use NavDirections type
    }
}