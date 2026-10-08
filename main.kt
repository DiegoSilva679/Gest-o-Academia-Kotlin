// Enumeração para representar o status do plano
enum class StatusPlano {
    ATIVO, BLOQUEADO, CANCELADO
}

// 1. Modelagem das Classes (Regra de Negócio 1)
data class Plano(
    val nome: String,
    val valorMensal: Double,
    val status: StatusPlano
)

data class Restricao(
    val laudoMedico: String? = null // Null Safety: campo opcional (Regra de Negócio 5)
)

class Aluno(
    val nome: String,
    val peso: Double,
    val altura: Double,
    val plano: Plano,
    val possuiFamiliarMatriculado: Boolean,
    val restricao: Restricao? = null
) {
    // 2. Cálculo do IMC (Regra de Negócio 2)
    fun calcularIMC(): Double {
        return peso / (altura * altura)
    }

    // 4. Cálculo do Desconto Familiar (Regra de Negócio 4)
    fun calcularValorMensalidade(): Double {
        val valorBase = plano.valorMensal
        return if (possuiFamiliarMatriculado) {
            valorBase * 0.85 // 15% de desconto
        } else {
            valorBase
        }
    }

    // 3. Avaliação do Status da Matrícula (Regra de Negócio 3)
    fun emitirAvisoStatus(): String {
        return when (plano.status) {
            StatusPlano.ATIVO -> "Acesso liberado. Bons treinos!"
            StatusPlano.BLOQUEADO -> "Aviso: Matrícula bloqueada por pendência financeira/cadastral."
            StatusPlano.CANCELADO -> "Aviso: Matrícula cancelada. Entre em contato com a recepção."
        }
    }

    // 5. Avaliação da Restrição Médica (Regra de Negócio 5)
    fun obterAptidaoMedica(): String {
        val laudo = restricao?.laudoMedico
        return if (laudo != null) {
            "Restrição detectada: $laudo"
        } else {
            "Sem restrições médicas registradas. Apto para exercícios intensos."
        }
    }
}

fun main() {
    println("=== SISTEMA DE GESTÃO DE ACADEMIA ===\n")

    // Criando planos
    val planoGold = Plano("Gold", 120.0, StatusPlano.ATIVO)
    val planoSilver = Plano("Silver", 90.0, StatusPlano.BLOQUEADO)
    val planoVIP = Plano("VIP", 200.0, StatusPlano.CANCELADO)

    // Lista de alunos (carrinho de cadastros)
    val cadastrosAlunos = listOf(
        Aluno(
            nome = "Lucas Andrade",
            peso = 82.5,
            altura = 1.78,
            plano = planoGold,
            possuiFamiliarMatriculado = true, // Recebe 15% de desconto
            restricao = Restricao("Cardiopatia leve - Evitar picos de frequência cardíaca")
        ),
        Aluno(
            nome = "Mariana Lima",
            peso = 61.0,
            altura = 1.65,
            plano = planoGold,
            possuiFamiliarMatriculado = false,
            restricao = Restricao(laudoMedico = null) // Campo laudo nulo
        ),
        Aluno(
            nome = "Carlos Eduardo",
            peso = 95.0,
            altura = 1.80,
            plano = planoSilver,
            possuiFamiliarMatriculado = true,
            restricao = null // Sem objeto restrição
        ),
        Aluno(
            nome = "Fernanda Souza",
            peso = 58.0,
            altura = 1.60,
            plano = planoVIP,
            possuiFamiliarMatriculado = false
        )
    )

    // Processamento com laço for iterando sobre a lista (Requisito de Laços)
    for (aluno in cadastrosAlunos) {
        println("--------------------------------------------------")
        println("Aluno: ${aluno.nome}")
        println("Plano: ${aluno.plano.nome} (R$ %.2f/mês)".format(aluno.plano.valorMensal))
        println("Status da Matrícula: ${aluno.plano.status} -> ${aluno.emitirAvisoStatus()}")
        println("IMC: %.2f".format(aluno.calcularIMC()))
        
        if (aluno.possuiFamiliarMatriculado) {
            println("Desconto Familiar: Sim (15% aplicado)")
        } else {
            println("Desconto Familiar: Não")
        }
        
        println("Mensalidade Final: R$ %.2f".format(aluno.calcularValorMensalidade()))
        println("Saúde & Aptidão: ${aluno.obterAptidaoMedica()}")
    }
    println("--------------------------------------------------")
}
