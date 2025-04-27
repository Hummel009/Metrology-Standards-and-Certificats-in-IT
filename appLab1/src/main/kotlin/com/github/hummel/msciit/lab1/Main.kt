package com.github.hummel.msciit.lab1

import com.formdev.flatlaf.FlatLightLaf
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTGitHubDarkIJTheme
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.GridLayout
import javax.swing.*
import javax.swing.border.EmptyBorder

fun main() {
	FlatLightLaf.setup()
	EventQueue.invokeLater {
		try {
			UIManager.setLookAndFeel(FlatMTGitHubDarkIJTheme())
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

	private fun process(inputField: JTextField, outputField: JTextField) {
		val outputPath = outputField.text
		val inputPath = inputField.text

		if (inputPath.isEmpty() || outputPath.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please select files", "Error", JOptionPane.ERROR_MESSAGE)
			return
		}

		val machine = Halstead(inputPath, outputPath)
		machine.calculate()
		JOptionPane.showMessageDialog(this, "Complete", "Message", JOptionPane.INFORMATION_MESSAGE)
	}

	init {
		title = "Halstead Metrics"
		defaultCloseOperation = EXIT_ON_CLOSE
		setBounds(100, 100, 450, 150)

		val contentPanel = JPanel()
		contentPanel.border = EmptyBorder(5, 5, 5, 5)
		contentPanel.layout = BorderLayout(0, 0)
		contentPanel.layout = GridLayout(0, 1, 0, 0)
		contentPane = contentPanel

		val inputPanel = JPanel()
		val inputLabel = JLabel("Input path:")
		inputLabel.preferredSize = Dimension(80, inputLabel.preferredSize.height)
		val inputField = JTextField(20)
		val inputButton = JButton("Select path")
		inputButton.addActionListener { selectPath(inputField) }
		inputPanel.add(inputLabel)
		inputPanel.add(inputField)
		inputPanel.add(inputButton)

		val outputPanel = JPanel()
		val outputLabel = JLabel("Output path:")
		outputLabel.preferredSize = Dimension(80, outputLabel.preferredSize.height)
		val outputField = JTextField(20)
		val outputButton = JButton("Select path")
		outputButton.addActionListener { selectPath(inputField) }
		outputPanel.add(outputLabel)
		outputPanel.add(outputField)
		outputPanel.add(outputButton)

		val processPanel = JPanel()
		val processButton = JButton("Process")
		processButton.addActionListener { process(inputField, outputField) }
		processPanel.add(processButton)

		contentPanel.add(inputPanel)
		contentPanel.add(outputPanel)
		contentPanel.add(processPanel)

		setLocationRelativeTo(null)
	}
}