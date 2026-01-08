import { createContext, useContext, useMemo, useState } from "react";
import { Snackbar, Alert } from "@mui/material";

const UiFeedbackContext = createContext(null);

export function UiFeedbackProvider({ children }) {
  const [snack, setSnack] = useState({ open: false, message: "", severity: "info" });

  const showError = (message) => setSnack({ open: true, message, severity: "error" });
  const showInfo = (message) => setSnack({ open: true, message, severity: "info" });

  const value = useMemo(() => ({ showError, showInfo }), []);

  return (
    <UiFeedbackContext.Provider value={value}>
      {children}

      <Snackbar
        open={snack.open}
        autoHideDuration={4000}
        onClose={() => setSnack((s) => ({ ...s, open: false }))}
        anchorOrigin={{ vertical: "top", horizontal: "center" }}
      >
        <Alert
          onClose={() => setSnack((s) => ({ ...s, open: false }))}
          severity={snack.severity}
          variant="filled"
        >
          {snack.message}
        </Alert>
      </Snackbar>
    </UiFeedbackContext.Provider>
  );
}

export function useUiFeedback() {
  const ctx = useContext(UiFeedbackContext);
  if (!ctx) throw new Error("useUiFeedback deve ser usado dentro de UiFeedbackProvider");
  return ctx;
}
