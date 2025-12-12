package mx.edu.utez.dulcedelicias.data.network.repository

import PedidoAPI
import android.content.Context
import mx.edu.utez.dulcedelicias.data.network.dao.PedidoDao
import mx.edu.utez.dulcedelicias.data.network.model.PedidoRequest
import mx.edu.utez.dulcedelicias.data.network.model.PedidoResponse

class PedidoRepository(context: Context) : PedidoDao {
    private val api = PedidoAPI(context)

    override fun create(
        pedido: PedidoRequest,
        onSuccess: (PedidoResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        api.create(pedido, onSuccess, onError)
    }
}



