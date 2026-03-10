import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
  stages: [
    { duration: '30s', target: 400 }, // Sobe para x usuários em 30s
    { duration: '1m', target: 400 },  // Mantém x usuários por 1 minuto
    { duration: '10s', target: 0 },  // Desce para 0
  ],
};

export default function () {
  const url = 'http://localhost:8082/fusion/create-account';
  const payload = JSON.stringify({
      firstName: "Diego",
      lastName: "Greene",
      cpf: "999.888.777-11",
      birthDate: "1959-05-14",
      phone: "+55 75 95555-3333",
      address: "Praça das 48 Leis, 48",
      email: "greene.laws@fusionbank.com",
      region: "Nordeste",
      city: "Feira de Santana",
      state: "BA",
      cep: "44004-700",
      password: "law_number_one_never_outshine"
  });

  const params = {
    headers: { 'Content-Type': 'application/json' },
  };

  const res = http.post(url, payload, params);
  
  check(res, {
    'status é 200 ou 201': (r) => r.status === 200 || r.status === 201,
  });

  sleep(1); 
}