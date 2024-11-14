-- Inserindo dados para a tabela Medico
INSERT INTO medico (id, nome, numero_registro, especializacao, endereco_id, contato_telefone, contato_email, disponibilidade_id) VALUES
(1, 'Dr. João Silva', '123456789', 'Cardiologista', 1, '31987654321', 'joao@exemplo.com', 1),
(2, 'Dra. Maria Oliveira', '987654321', 'Pediatra', 2, '31987654322', 'maria@exemplo.com', 2),
(3, 'Dr. Carlos Souza', '112233445', 'Neurologista', 3, '31987654323', 'carlos@exemplo.com', 3);


-- Inserindo dados para a tabela Paciente
INSERT INTO paciente (id, nome, data_nascimento, genero, endereco_id, contato_telefone, contato_email) VALUES
(1, 'Ana Paula Santos', '1990-05-15', 'FEMININO', 1, '31987654324', 'ana@exemplo.com'),
(2, 'Ricardo Pereira', '1985-11-25', 'MASCULINO', 2, '31987654325', 'ricardo@exemplo.com'),
(3, 'Juliana Costa', '2000-02-10', 'FEMININO', 3, '31987654326', 'juliana@exemplo.com');

-- Inserindo dados para a tabela Consulta
INSERT INTO consulta (id, data_hora, motivo_consulta, observacoes_medicas, paciente_id, medico_id) VALUES
(1, '2024-11-01 08:00:00', 'Consulta de rotina', 'Paciente em bom estado de saúde', 1, 1),
(2, '2024-11-02 09:30:00', 'Exame de acompanhamento', 'Paciente com histórico de hipertensão', 2, 2),
(3, '2024-11-03 14:00:00', 'Consulta neurológica', 'Paciente com queixa de dores de cabeça frequentes', 3, 3);

-- Inserindo dados para a tabela HistoricoMedico
INSERT INTO historico_medico (id, condicao, tratamento, alergias, medicamentos_em_uso, paciente_id) VALUES
(1, 'Hipertensão', 'Uso diário de medicamento para controle de pressão', 'Nenhuma', 'Losartana', 1),
(2, 'Asma', 'Uso de broncodilatador em crise', 'Alergia a pólen', 'Salbutamol', 2),
(3, 'Enxaqueca', 'Analgésicos e repouso', 'Nenhuma', 'Paracetamol', 3);

-- Inserindo dados para a tabela Exame
INSERT INTO exame (id, nome_exame, resultado, data_exame, consulta_id) VALUES
(1, 'ECG', 'Normal', '2024-11-01 08:30:00', 1),
(2, 'Exame de sangue', 'Alterado - colesterol elevado', '2024-11-02 10:00:00', 2),
(3, 'Tomografia', 'Sem alterações significativas', '2024-11-03 14:30:00', 3);

-- Inserindo dados para a tabela Prescricao
INSERT INTO prescricao (id, medicamento, dosagem, instrucoes_uso, consulta_id) VALUES
(1, 'Losartana', '50mg', 'Tomar 1 comprimido por dia', 1),
(2, 'Salbutamol', '2 inalações', 'Usar durante crise asmática', 2),
(3, 'Paracetamol', '500mg', 'Tomar a cada 8 horas se necessário', 3);

-- Inserindo dados para a tabela Disponibilidade
INSERT INTO disponibilidade (id, inicio, fim, disponivel, medico_id) VALUES
(1, '2024-11-01 08:00:00', '2024-11-01 12:00:00', true, 1),
(2, '2024-11-02 09:00:00', '2024-11-02 13:00:00', true, 2),
(3, '2024-11-03 14:00:00', '2024-11-03 18:00:00', true, 3);

-- Inserindo dados para a tabela Endereco
INSERT INTO endereco (id, rua, cep, bairro, numero, cidade, complemento) VALUES
(1, 'Rua das Flores', '12345-678', 'Centro', '123', 'Belo Horizonte', 'Apto 101'),
(2, 'Av. Brasil', '23456-789', 'Savassi', '456', 'Belo Horizonte', 'Apto 202'),
(3, 'Rua São Paulo', '34567-890', 'Pampulha', '789', 'Belo Horizonte', 'Casa 303');
