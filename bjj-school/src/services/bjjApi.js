import axios from "axios";
import keycloak from "../auth/keycloak";

const bjjApi = axios.create({
  baseURL: "http://localhost:8090/api",
  timeout: 10000,
});

bjjApi.interceptors.request.use(async (config) => {
  // se já existe token, renova e envia
  if (keycloak?.token) {
    await keycloak.updateToken(30);
    config.headers.Authorization = `Bearer ${keycloak.token}`;
  }
  return config;
});

// aqui a “ponte” para UI: a gente seta um handler global
let onAuthError = null;
export function setApiAuthErrorHandler(fn) {
  onAuthError = fn;
}

bjjApi.interceptors.response.use(
  (res) => res,
  (error) => {
    const status = error?.response?.status;

    if (status === 403 && onAuthError) {
      onAuthError("Você não tem permissão para executar esta ação.");
    }

    if (status === 401 && onAuthError) {
      if (keycloak?.token) onAuthError("Sua sessão expirou ou você não está autenticado.");
    }

    return Promise.reject(error);
  }
);

export default bjjApi;
