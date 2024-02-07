package com.github.hummel.msciit.lab2

import com.formdev.flatlaf.FlatLightLaf
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme
import com.github.hummel.msciit.lab2.lexer.Lexer
import com.github.hummel.msciit.lab2.parser.RParser
import java.awt.BorderLayout
import java.awt.EventQueue
import java.awt.GridLayout
import javax.swing.*
import javax.swing.border.EmptyBorder

fun main() {
	FlatLightLaf.setup()
	EventQueue.invokeLater {
		try {
			UIManager.setLookAndFeel(FlatGitHubDarkIJTheme())
			val frame = GUI()
			frame.isVisible = true
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}

class GUI : JFrame() {
	private fun selectPath(pathField: JTextField) {
		val fileChooser = JFileChooser()
		val result = fileChooser.showOpenDialog(this)
		if (result == JFileChooser.APPROVE_OPTION) {
			pathField.text = fileChooser.selectedFile.absolutePath
		}
	}

	private fun process(inputField: JTextField) {
		val inputPath = inputField.text
		if (inputPath.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE)
			return
		}

		val jilbe = Jilbe()
		val lexer = Lexer(inputPath)
		val parser = RParser(lexer, jilbe)
		parser.parse(false)

		val nest = jilbe.getMaxNest()
		val conditions = jilbe.conditionAmount
		val operators = jilbe.operatorAmount
		val saturation = jilbe.getConditionSaturation()

		JOptionPane.showMessageDialog(
			this,
			"MAX NEST (CLI): $nest \r\nCONDITIONS (CL): $conditions\r\nOPERATORS: $operators\r\nSATURATION (cl): $saturation",
			"Message",
			JOptionPane.INFORMATION_MESSAGE
		)
	}

	init {
		title = "Jilbe Metrics"
		defaultCloseOperation = EXIT_ON_CLOSE
		setBounds(100, 100, 500, 120)

		val contentPanel = JPanel()
		contentPanel.border = EmptyBorder(5, 5, 5, 5)
		contentPanel.layout = BorderLayout(0, 0)
		contentPanel.layout = GridLayout(0, 1, 0, 0)
		contentPane = contentPanel

		val inputPanel = JPanel()
		val inputLabel = JLabel("Input path:")
		val inputField = JTextField(24)
		val inputButton = JButton("Select path")
		inputButton.addActionListener { selectPath(inputField) }
		inputPanel.add(inputLabel)
		inputPanel.add(inputField)
		inputPanel.add(inputButton)

		val buttonPanel = JPanel()
		val buttonProcess = JButton("Process")
		buttonProcess.addActionListener { process(inputField) }
		buttonPanel.add(buttonProcess)

		contentPanel.add(inputPanel)
		contentPanel.add(buttonPanel)

		setLocationRelativeTo(null)
	}
}