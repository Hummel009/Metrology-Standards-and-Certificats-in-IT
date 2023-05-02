
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.GridLayout
import java.math.BigInteger
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
	private var mode = SignMode.RUS

	private fun selectPath(pathField: JTextField) {
		val fileChooser = JFileChooser()
		val result = fileChooser.showOpenDialog(this)
		if (result == JFileChooser.APPROVE_OPTION) {
			pathField.text = fileChooser.selectedFile.absolutePath
		}
	}

	private fun error(
		inputField: JTextField,
		outputField: JTextField,
		keyFieldQ: JTextField,
		keyFieldP: JTextField,
		keyFieldH: JTextField,
		keyFieldX: JTextField,
		keyFieldY: JTextField,
		keyFieldK: JTextField,
		keyFieldM: JTextField
	): Boolean {
		if (inputField.text.isEmpty() || outputField.text.isEmpty() || keyFieldQ.text.isEmpty() || keyFieldP.text.isEmpty() || keyFieldH.text.isEmpty() || keyFieldK.text.isEmpty() || keyFieldM.text.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Empty fields", "Error", JOptionPane.ERROR_MESSAGE)
			return true
		}
		try {
			val q = keyFieldQ.text.toBigInteger()
			val p = keyFieldP.text.toBigInteger()
			val h = keyFieldH.text.toBigInteger()
			val x = keyFieldX.text.toBigInteger()
			val k = keyFieldK.text.toBigInteger()
			keyFieldM.text.toBigInteger()
			keyFieldY.text.toBigInteger()
			ValuesChecker.checkQ(q)
			ValuesChecker.checkP(p, q)
			ValuesChecker.checkH(q, p, h)
			ValuesChecker.checkInterval(BigInteger.ZERO, q, x)
			ValuesChecker.checkInterval(BigInteger.ONE, q - BigInteger.ONE, k)
		} catch (e: Exception) {
			JOptionPane.showMessageDialog(this, "Wrong data", "Error", JOptionPane.ERROR_MESSAGE)
			return true
		}
		return false
	}

	private fun design(
		inputField: JTextField,
		outputField: JTextField,
		keyFieldQ: JTextField,
		keyFieldP: JTextField,
		keyFieldH: JTextField,
		keyFieldX: JTextField,
		keyFieldY: JTextField,
		keyFieldK: JTextField,
		keyFieldM: JTextField
	) {
		val error = error(inputField, outputField, keyFieldQ, keyFieldP, keyFieldH, keyFieldX, keyFieldY, keyFieldK, keyFieldM)

		if (keyFieldY.text.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Select Y", "Error", JOptionPane.ERROR_MESSAGE)
			return
		}

		if (!error) {
			val inputPath = inputField.text
			val outputPath = outputField.text
			val q = keyFieldQ.text.toBigInteger()
			val p = keyFieldP.text.toBigInteger()
			val h = keyFieldH.text.toBigInteger()
			val y = keyFieldY.text.toBigInteger()
			val k = keyFieldK.text.toBigInteger()
			val m = keyFieldM.text.toBigInteger()

			val signer = Signer(inputPath, outputPath, q, p, h, k, m)
			val cortege = try {
				signer.design(mode, y)
			} catch (e: Exception) {
				null
			}

			if (cortege != null) {
				val hash = cortege.value1
				val r = cortege.value2
				val s = cortege.value3
				val w = cortege.value4
				val u1 = cortege.value5
				val u2 = cortege.value6
				val v = cortege.value7
				JOptionPane.showMessageDialog(
					this,
					"Hash = $hash, R = $r, S = $s, W = $w, U1 = $u1, U2 = $u2, V = $v\r\n${r == v}",
					"Message",
					JOptionPane.INFORMATION_MESSAGE
				)
				keyFieldX.text = "45"
				keyFieldY.text = "0"
			} else {
				JOptionPane.showMessageDialog(
					this, "Broken file", "Error", JOptionPane.ERROR_MESSAGE
				)
			}
		}
	}

	private fun ensign(
		inputField: JTextField,
		outputField: JTextField,
		keyFieldQ: JTextField,
		keyFieldP: JTextField,
		keyFieldH: JTextField,
		keyFieldX: JTextField,
		keyFieldY: JTextField,
		keyFieldK: JTextField,
		keyFieldM: JTextField
	) {
		val error = error(inputField, outputField, keyFieldQ, keyFieldP, keyFieldH, keyFieldX, keyFieldY, keyFieldK, keyFieldM)

		if (keyFieldX.text.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Select X", "Error", JOptionPane.ERROR_MESSAGE)
			return
		}

		if (!error) {
			val inputPath = inputField.text
			val outputPath = outputField.text
			val q = keyFieldQ.text.toBigInteger()
			val p = keyFieldP.text.toBigInteger()
			val h = keyFieldH.text.toBigInteger()
			val x = keyFieldX.text.toBigInteger()
			val k = keyFieldK.text.toBigInteger()
			val m = keyFieldM.text.toBigInteger()

			val signer = Signer(inputPath, outputPath, q, p, h, k, m)
			val cortege = try {
				signer.ensign(mode, x)
			} catch (e: Exception) {
				null
			}

			if (cortege != null) {
				val hash = cortege.value1
				val r = cortege.value2
				val s = cortege.value3
				val y = cortege.value4
				JOptionPane.showMessageDialog(
					this, "Hash = $hash, r = $r, s = $s, y = $y", "Message", JOptionPane.INFORMATION_MESSAGE
				)
				keyFieldX.text = "0"
				keyFieldY.text = "$y"
			} else {
				JOptionPane.showMessageDialog(
					this, "Broken file", "Error", JOptionPane.ERROR_MESSAGE
				)
			}
		}
	}

	init {
		title = "DSA Signer Machine"
		defaultCloseOperation = EXIT_ON_CLOSE
		setBounds(100, 100, 550, 250)

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

		val keyPanel1 = JPanel()
		val keyLabelQ = JLabel("Q:")
		keyLabelQ.preferredSize = Dimension(15, keyLabelQ.preferredSize.height)
		val keyFieldQ = JTextField(8)
		keyFieldQ.text = "107"
		val keyLabelP = JLabel("P:")
		keyLabelP.preferredSize = Dimension(15, keyLabelP.preferredSize.height)
		val keyFieldP = JTextField(8)
		keyFieldP.text = "643"
		val keyLabelH = JLabel("H:")
		keyLabelH.preferredSize = Dimension(15, keyLabelH.preferredSize.height)
		val keyFieldH = JTextField(8)
		keyFieldH.text = "2"
		val keyLabelK = JLabel("K:")
		keyLabelK.preferredSize = Dimension(15, keyLabelK.preferredSize.height)
		val keyFieldK = JTextField(8)
		keyFieldK.text = "31"
		keyPanel1.add(keyLabelQ)
		keyPanel1.add(keyFieldQ)
		keyPanel1.add(keyLabelP)
		keyPanel1.add(keyFieldP)
		keyPanel1.add(keyLabelH)
		keyPanel1.add(keyFieldH)
		keyPanel1.add(keyLabelK)
		keyPanel1.add(keyFieldK)

		val keyPanel2 = JPanel()
		val keyLabelX = JLabel("X:")
		keyLabelX.preferredSize = Dimension(15, keyLabelX.preferredSize.height)
		val keyFieldX = JTextField(8)
		keyFieldX.text = "45"
		val keyLabelY = JLabel("Y:")
		keyLabelY.preferredSize = Dimension(15, keyLabelY.preferredSize.height)
		val keyFieldY = JTextField(8)
		keyFieldY.text = "0"
		val keyLabelM = JLabel("M:")
		keyLabelM.preferredSize = Dimension(15, keyLabelM.preferredSize.height)
		val keyFieldM = JTextField(8)
		keyFieldM.text = "323"
		keyPanel2.add(keyLabelX)
		keyPanel2.add(keyFieldX)
		keyPanel2.add(keyLabelY)
		keyPanel2.add(keyFieldY)
		keyPanel2.add(keyLabelM)
		keyPanel2.add(keyFieldM)

		val radioPanel = JPanel()
		val radioButtonRus = JRadioButton("RUS")
		val radioButtonEng = JRadioButton("ENG")
		val radioButtonAsc = JRadioButton("ASC")
		val radioButtonBin = JRadioButton("BIN")
		radioButtonRus.isSelected = true

		radioButtonRus.addActionListener {
			mode = SignMode.RUS
			radioButtonEng.isSelected = false
			radioButtonAsc.isSelected = false
			radioButtonBin.isSelected = false
		}
		radioButtonEng.addActionListener {
			mode = SignMode.ENG
			radioButtonRus.isSelected = false
			radioButtonAsc.isSelected = false
			radioButtonBin.isSelected = false
		}
		radioButtonAsc.addActionListener {
			mode = SignMode.ASC
			radioButtonRus.isSelected = false
			radioButtonEng.isSelected = false
			radioButtonBin.isSelected = false
		}
		radioButtonBin.addActionListener {
			mode = SignMode.BIN
			radioButtonRus.isSelected = false
			radioButtonAsc.isSelected = false
			radioButtonEng.isSelected = false
		}

		radioPanel.add(radioButtonRus)
		radioPanel.add(radioButtonEng)
		radioPanel.add(radioButtonAsc)
		radioPanel.add(radioButtonBin)

		val processPanel = JPanel()
		val processEnsign = JButton("Ensign")
		val processDesign = JButton("Design")
		processEnsign.addActionListener {
			ensign(inputField, outputField, keyFieldQ, keyFieldP, keyFieldH, keyFieldX, keyFieldY, keyFieldK, keyFieldM)
		}
		processDesign.addActionListener {
			design(inputField, outputField, keyFieldQ, keyFieldP, keyFieldH, keyFieldX, keyFieldY, keyFieldK, keyFieldM)
		}
		processPanel.add(processEnsign)
		processPanel.add(processDesign)

		contentPanel.add(inputPanel)
		contentPanel.add(outputPanel)
		contentPanel.add(keyPanel1)
		contentPanel.add(keyPanel2)
		contentPanel.add(radioPanel)
		contentPanel.add(processPanel)

		setLocationRelativeTo(null)
	}
}

enum class SignMode {
	RUS, ENG, ASC, BIN
}

data class CortegeFour(
	val value1: Any, val value2: Any, val value3: Any, val value4: Any
)

data class CortegeSeven(
	val value1: Any,
	val value2: Any,
	val value3: Any,
	val value4: Any,
	val value5: Any,
	val value6: Any,
	val value7: Any
)