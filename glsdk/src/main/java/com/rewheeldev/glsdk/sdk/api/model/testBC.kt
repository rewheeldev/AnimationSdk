//package com.rewheeldev.glsdk.sdk.api.model
//
//import java.security.MessageDigest
//import java.util.Date
//
//data class Block(
//    val index: Int,
//    val timestamp: Long,
//    val data: Any,
//    val previousHash: String,
//    val hash: String
//)
//
//class Blockchain {
//    val blockchain = mutableListOf<Block>()
//
//    init {
//        // Создание блока "генезиса"
//        createBlock("Genesis Block")
//    }
//
//    private fun createBlock(data: Any): Block {
//        val previousBlock = blockchain.lastOrNull()
//        val index = previousBlock?.index?.plus(1) ?: 0
//        val timestamp = Date().time
//        val previousHash = previousBlock?.hash ?: ""
//        val hash = calculateHash(index, timestamp, data, previousHash)
//
//        val block = Block(index, timestamp, data, previousHash, hash)
//        blockchain.add(block)
//        return block
//    }
//
//    private fun calculateHash(index: Int, timestamp: Long, data: Any, previousHash: String): String {
//        val input = "$index$timestamp$data$previousHash"
//        val sha256 = MessageDigest.getInstance("SHA-256")
//        val hashBytes = sha256.digest(input.toByteArray())
//        return hashBytes.joinToString("") { "%02x".format(it) }
//    }
//
//    fun addBlock(data: Any): Block {
//        return createBlock(data)
//    }
//
//    fun getLatestBlock(): Block {
//        return blockchain.last()
//    }
//
//    fun isValid(): Boolean {
//        for (i in 1 until blockchain.size) {
//            val currentBlock = blockchain[i]
//            val previousBlock = blockchain[i - 1]
//
//            if (currentBlock.hash != calculateHash(
//                    currentBlock.index,
//                    currentBlock.timestamp,
//                    currentBlock.data,
//                    currentBlock.previousHash
//                )
//            ) {
//                return false
//            }
//
//            if (currentBlock.previousHash != previousBlock.hash) {
//                return false
//            }
//        }
//        return true
//    }
//}
//
//fun main() {
////    val blockchain = Blockchain()
////
////    blockchain.addBlock("get my balance")
////    blockchain.addBlock("100.03")
////
////    println("Blockchain is valid: ${blockchain.isValid()}")
////    println("Blockchain:")
////    blockchain.blockchain.forEach { block ->
////        println("Index: ${block.index}")
////        println("Timestamp: ${block.timestamp}")
////        println("Data: ${block.data}")
////        println("Previous Hash: ${block.previousHash}")
////        println("Hash: ${block.hash}")
////        println()
////    }
//    firstSender()
//}
//
//fun server(msg:Block):Block{
//
//    val blockchain = Blockchain()
//    blockchain.blockchain.add(msg)
//    blockchain.addBlock("0")
//    println("${blockchain.getLatestBlock()}")
//    return blockchain.getLatestBlock()
//}
//
//fun firstSender(){
//    val blockchain = Blockchain()
//    blockchain.addBlock("get my balance")
//
//    val receiveMsg = server(blockchain.getLatestBlock())
//    blockchain.blockchain.add(receiveMsg)
//
//    println("Blockchain is valid: ${blockchain.isValid()}")
//
//    println("Blockchain:")
//    blockchain.blockchain.forEach { block ->
//        println("Index: ${block.index}")
//        println("Timestamp: ${block.timestamp}")
//        println("Data: ${block.data}")
//        println("Previous Hash: ${block.previousHash}")
//        println("Hash: ${block.hash}")
//        println()
//    }
//
//}