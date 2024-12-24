package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var gridLayout: GridLayout? = null
    private var gameStatus: TextView? = null
    private var board = Array(3) { Array(3) { "" } }
    private var currentPlayer = "X"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gridLayout = findViewById(R.id.gridLayout)
        gameStatus = findViewById(R.id.gameStatus)

        // Set click listeners for all buttons in the grid
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val buttonId = "button_${i}_$j"
                val resID = resources.getIdentifier(buttonId, "id", packageName)
                val button = findViewById<Button>(resID)
                button.setOnClickListener { onCellClicked(i, j, button) }
            }
        }
    }

    private fun onCellClicked(row: Int, col: Int, button: Button) {
        // If cell is already occupied, do nothing
        if (board[row][col] != "") return

        // Set the current player mark (X or O)
        board[row][col] = currentPlayer
        button.text = currentPlayer

        // Check if the current player wins
        if (checkWinner()) {
            gameStatus?.text = "$currentPlayer wins!"
            disableButtons()
            return
        }

        // Check for draw
        if (isBoardFull()) {
            gameStatus?.text = "It's a draw!"
            disableButtons()
            return
        }

        // Switch to the other player
        currentPlayer = if (currentPlayer == "X") "O" else "X"
        gameStatus?.text = "$currentPlayer PLAY"
    }

    private fun checkWinner(): Boolean {
        // Check rows, columns and diagonals for a win
        for (i in 0 until 3) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (board[i][j] == "") return false
            }
        }
        return true
    }

    private fun disableButtons() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val buttonId = "button_${i}_$j"
                val resID = resources.getIdentifier(buttonId, "id", packageName)
                val button = findViewById<Button>(resID)
                button.isEnabled = false
            }
        }
    }
}