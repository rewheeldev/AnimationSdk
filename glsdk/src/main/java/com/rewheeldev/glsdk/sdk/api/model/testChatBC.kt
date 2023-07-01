//import java.net.ServerSocket
//import java.net.Socket
//import java.security.MessageDigest
//import java.io.BufferedReader
//import java.io.InputStreamReader
//import java.io.PrintWriter
//
//// Класс, представляющий сообщение в чате
//data class ChatMessage(val sender: String, val content: String, val timestamp: Long, val hash: String)
//
//// Класс, представляющий блок в блокчейне
//data class Block(val previousHash: String, val message: ChatMessage)
//
//// Класс, представляющий блокчейн
//class Blockchain {
//    private val chain: MutableList<Block> = mutableListOf()
//
//    init {
//        // Создаем блок генезиса
//        val genesisBlock = Block("0", ChatMessage("System", "Genesis block", System.currentTimeMillis(), ""))
//        chain.add(genesisBlock)
//    }
//
//    // Добавление нового блока в блокчейн
//    fun addBlock(message: ChatMessage) {
//        val previousHash = getLastBlock().message.hash
//        val newBlock = Block(previousHash, message)
//        chain.add(newBlock)
//    }
//
//    // Получение последнего блока в блокчейне
//    private fun getLastBlock(): Block {
//        return chain.last()
//    }
//
//    // Проверка целостности блокчейна
//    fun isChainValid(): Boolean {
//        for (i in 1 until chain.size) {
//            val currentBlock = chain[i]
//            val previousBlock = chain[i - 1]
//
//            // Проверяем хеш предыдущего блока
//            if (currentBlock.previousHash != previousBlock.message.hash) {
//                return false
//            }
//
//            // Проверяем хеш текущего блока
//            val calculatedHash = calculateHash(currentBlock.message)
//            if (currentBlock.message.hash != calculatedHash) {
//                return false
//            }
//        }
//        return true
//    }
//
//    // Расчет хеша сообщения с использованием SHA-256
//    private fun calculateHash(message: ChatMessage): String {
//        val data = message.sender + message.content + message.timestamp + message.hash
//        val digest = MessageDigest.getInstance("SHA-256")
//        val hashBytes = digest.digest(data.toByteArray())
//        return hashBytes.joinToString("") { "%02x".format(it) }
//    }
//}
//
//class Server {
//    private val blockchain = Blockchain()
//    private val clients: MutableList<ClientHandler> = mutableListOf()
//
//    fun start() {
//        val serverSocket = ServerSocket(12345)
//        println("Server started on port 12345.")
//
//        while (true) {
//            val clientSocket = serverSocket.accept()
//            val clientHandler = ClientHandler(clientSocket)
//            clients.add(clientHandler)
//            clientHandler.start()
//        }
//    }
//
//    // Отправка сообщения всем клиентам
//    fun broadcastMessage(message: ChatMessage) {
//        for (client in clients) {
//            client.sendMessage(message)
//        }
//    }
//
//    // Добавление нового блока в блокчейн
//    fun addBlockToBlockchain(message: ChatMessage) {
//        blockchain.addBlock(message)
//        broadcastMessage(message)
//    }
//
//    // Проверка целостности блокчейна
//    fun isBlockchainValid(): Boolean {
//        return blockchain.isChainValid()
//    }
//}
//
//
//class ClientHandler(private val clientSocket: Socket, private val server: Server) : Thread() {
//    private lateinit var reader: BufferedReader
//    private lateinit var writer: PrintWriter
//
//    init {
//        try {
//            reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
//            writer = PrintWriter(clientSocket.getOutputStream(), true)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    override fun run() {
//        try {
//            // Принимаем имя пользователя от клиента
//            val username = reader.readLine()
//            println("New client connected: $username")
//
//            // Создаем нового пользователя и добавляем его в список клиентов
//            val client = Client(username, clientSocket)
//            server.addClient(client)
//
//            // Отправляем клиенту текущее состояние блокчейна
//            for (block in server.getBlockchain()) {
//                sendMessage(block.message)
//            }
//
//            while (true) {
//                // Принимаем сообщение от клиента
//                val content = reader.readLine()
//                if (content.isNullOrEmpty()) {
//                    break
//                }
//
//                // Создаем новое сообщение
//                val timestamp = System.currentTimeMillis()
//                val message = ChatMessage(username, content, timestamp, "")
//                server.addBlockToBlockchain(message)
//            }
//
//            // Клиент отключился
//            server.removeClient(client)
//            reader.close()
//            writer.close()
//            clientSocket.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    // Отправка сообщения клиенту
//    fun sendMessage(message: ChatMessage) {
//        writer.println(message.sender)
//        writer.println(message.content)
//        writer.println(message.timestamp)
//        writer.println(message.hash)
//        writer.println() // Разделитель между сообщениями
//    }
//}
//
