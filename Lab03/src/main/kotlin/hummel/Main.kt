package hummel

import hummel.inter.Id
import hummel.lexer.Lexer
import hummel.parser.RParser
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.GridLayout
import java.io.File
import javax.swing.*
import javax.swing.border.EmptyBorder

fun main() {
	EventQueue.invokeLater {
		try {
			for (info in UIManager.getInstalledLookAndFeels()) {
				if ("Windows Classic" == info.name) {
					UIManager.setLookAndFeel(info.className)
					break
				}
			}
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
		val inputPath = inputField.text
		val outputPath = outputField.text
		if (inputPath.isEmpty() || outputPath.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Empty fields", "Error", JOptionPane.ERROR_MESSAGE)
			return
		}

		val metrics = Chepin()
		val parser = RParser(Lexer(inputPath), metrics)
		parser.parse(false)
		val sb = StringBuilder()

		val span = metrics.getSpans()
		sb.append("<style>\r\nbody {\r\n\tbackground: #666 \r\n}\r\ntable {\r\n\tborder-collapse: collapse;\r\n\twidth: 100%;\r\n\tmax-width: 800px;\r\n\tmargin: 0 auto;\r\n}\r\nth, td {\r\n\tpadding: 8px;\r\n\ttext-align: left;\r\n\tborder-bottom: 1px solid #ddd;\r\n}\r\nth {\r\n\tbackground-color: #bcbcbc;\r\n}\r\ntd {\r\n\tbackground: White;\r\n}\r\ntr:hover {\r\n\tbackground-color: #f5f5f5;\r\n}\r\n</style>\r\n")
		sb.append("<table>\n")
		sb.append("<tr><th>id</th><th>span</th></tr>\n")
		var sum = 0
		span.forEach {
			sb.append("<tr><td>${it.key}</td><td>${it.value}</td></tr>\n")
			sum += it.value
		}
		sb.append("<tr><th>sum</th><th>$sum</th></tr>\n")
		sb.append("</table>\n<br>\n")

		val id1 = metrics.getIds()
		sb.append("<table>\n")
		sb.append("<tr><th>Group</th><th>P</th><th>M</th><th>C</th><th>T</th></tr>\n")
		val p1 = mutableListOf<Id>()
		val m1 = mutableListOf<Id>()
		val c1 = mutableListOf<Id>()
		val t1 = mutableListOf<Id>()
		id1.forEach {
			when (it.value) {
				ChepinGroups.P -> p1.add(it.key)
				ChepinGroups.M -> m1.add(it.key)
				ChepinGroups.C -> c1.add(it.key)
				else -> t1.add(it.key)
			}
		}
		sb.append("<tr><td>Ids</td><td>$p1</td><td>$m1</td><td>$c1</td><td>$t1</td></tr>\n")
		sb.append("<tr><td>Quantity</td><td>${p1.size}</td><td>${m1.size}</td><td>${c1.size}</td><td>${t1.size}</td></tr>\n")
		sb.append("<tr><th>Q = ${metrics.getQ()}</th><th>1 * ${p1.size}</th><th>2 * ${m1.size}</th><th>3 * ${c1.size}</th><th>0.5 * ${t1.size}</th></tr>\n")
		sb.append("</table>\n<br>\n")

		val id2 = metrics.getIds()
		sb.append("<table>\n")
		sb.append("<tr><th>Group</th><th>P</th><th>M</th><th>C</th><th>T</th></tr>\n")
		val p2 = mutableListOf<Id>()
		val m2 = mutableListOf<Id>()
		val c2 = mutableListOf<Id>()
		val t2 = mutableListOf<Id>()
		id2.forEach {
			if (metrics.pBuffer.contains(it.key)) {
				when (it.value) {
					ChepinGroups.P -> p2.add(it.key)
					ChepinGroups.M -> m2.add(it.key)
					ChepinGroups.C -> c2.add(it.key)
					else -> t2.add(it.key)
				}
			}
		}
		sb.append("<tr><td>Ids</td><td>$p2</td><td>$m2</td><td>$c2</td><td>$t2</td></tr>\n")
		sb.append("<tr><td>Quantity</td><td>${p2.size}</td><td>${m2.size}</td><td>${c2.size}</td><td>${t2.size}</td></tr>\n")
		sb.append("<tr><th>Q = ${metrics.getQ(true)}</th><th>1 * ${p2.size}</th><th>2 * ${m2.size}</th><th>3 * ${c2.size}</th><th>0.5 * ${t2.size}</th></tr>\n")
		sb.append("</table>\n<br>\n")

		File(outputPath).writeText(sb.toString())

		JOptionPane.showMessageDialog(
			this,
			"Complete",
			"Message",
			JOptionPane.INFORMATION_MESSAGE
		)
	}

	init {
		title = "Chepin Metrics"
		defaultCloseOperation = EXIT_ON_CLOSE
		setBounds(100, 100, 550, 150)

		val contentPanel = JPanel()
		contentPanel.border = EmptyBorder(5, 5, 5, 5)
		contentPanel.layout = BorderLayout(0, 0)
		contentPanel.layout = GridLayout(0, 1, 0, 0)
		contentPane = contentPanel

		val inputPanel = JPanel()
		val inputLabel = JLabel("Input path:")
		inputLabel.preferredSize = Dimension(80, inputLabel.preferredSize.height)
		val inputField = JTextField(24)
		val inputButton = JButton("Select path")
		inputButton.addActionListener { selectPath(inputField) }
		inputPanel.add(inputLabel)
		inputPanel.add(inputField)
		inputPanel.add(inputButton)

		val outputPanel = JPanel()
		val outputLabel = JLabel("Output path:")
		outputLabel.preferredSize = Dimension(80, outputLabel.preferredSize.height)
		val outputField = JTextField(24)
		val outputButton = JButton("Select path")
		outputButton.addActionListener { selectPath(outputField) }
		outputPanel.add(outputLabel)
		outputPanel.add(outputField)
		outputPanel.add(outputButton)

		val buttonPanel = JPanel()
		val buttonProcess = JButton("Process")
		buttonProcess.addActionListener { process(inputField, outputField) }
		buttonPanel.add(buttonProcess)

		contentPanel.add(inputPanel)
		contentPanel.add(outputPanel)
		contentPanel.add(buttonPanel)

		setLocationRelativeTo(null)
	}
}