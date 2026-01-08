import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import { Provider, useSelector } from "react-redux";
import { store } from "./store";
import { ThemeProvider, CssBaseline } from "@mui/material";
import App from "./App";
import { AlunosProvider } from "./context/AlunosContext";
import { makeTheme } from "./theme/theme";
import { AuthProvider } from "./context/AuthContext";
import { UiFeedbackProvider } from "./context/UiFeedbackContext";

function WithTheme({ children }) {
  const mode = useSelector(s => s.ui.mode);
  const theme = makeTheme(mode);
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      {children}
    </ThemeProvider>
  );
}

ReactDOM.createRoot(document.getElementById("root")).render(

  <Provider store={store}>
    <WithTheme>
      <BrowserRouter>
        <AuthProvider>
          <UiFeedbackProvider>
            <AlunosProvider>
              <App />
            </AlunosProvider>
          </UiFeedbackProvider>
        </AuthProvider>
      </BrowserRouter>
    </WithTheme>
  </Provider>

);
