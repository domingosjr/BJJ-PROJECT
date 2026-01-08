import { useEffect } from "react";
import {
  AppBar,
  Toolbar,
  Typography,
  Container,
  Button,
  IconButton,
} from "@mui/material";
import { alpha } from "@mui/material/styles";
import { NavLink, useNavigate } from "react-router-dom";
import Brightness4Icon from "@mui/icons-material/Brightness4";
import Brightness7Icon from "@mui/icons-material/Brightness7";
import LogoutButton from "../components/LogoutButton";
import { useUiFeedback } from "../context/UiFeedbackContext";
import { setApiAuthErrorHandler } from "../services/bjjApi";
import { useAuth } from "../context/AuthContext";


// ✅ Redux
import { useDispatch, useSelector } from "react-redux";
// Ajuste o caminho/nome conforme seu projeto:
import { toggleMode } from "../store/uiSlice"; // <-- se o nome for outro, me diga

function NavButton({ to, children, end }) {
  return (
    <Button
      component={NavLink}
      to={to}
      end={end}
      variant="text"
      sx={(t) => {
        const contrast = t.palette.getContrastText(t.palette.primary.main);
        return {
          color: `${contrast} !important`,
          textTransform: "none",
          fontWeight: 500,
          borderRadius: 2,
          "&.active": {
            backgroundColor: alpha(contrast, 0.24),
            color: `${contrast} !important`,
          },
          "&:hover": {
            backgroundColor: alpha(contrast, 0.14),
          },
          "& .MuiButton-startIcon, & .MuiButton-endIcon": {
            color: `${contrast} !important`,
          },
        };
      }}
    >
      {children}
    </Button>
  );
}

export default function AppShell({ children }) {
  const navigate = useNavigate();

  // ✅ tema vem do WithTheme (Redux)
  const mode = useSelector((s) => s.ui.mode);
  const dispatch = useDispatch();

  const { showError } = useUiFeedback();
  const { hasRole } = useAuth();
  const isAdmin = hasRole("ADMIN");

  useEffect(() => {
    setApiAuthErrorHandler(showError);
  }, [showError]);

  const toggleTheme = () => {
    dispatch(toggleMode());
  };

  return (
    <>
      <AppBar position="static" color="primary" enableColorOnDark>
        <Toolbar sx={{ gap: 2 }}>
          <Typography
            variant="h6"
            sx={{ flexGrow: 1, cursor: "pointer" }}
            onClick={() => navigate("/")}
          >
            JJ School
          </Typography>

          <NavButton to="/" end>
            Home
          </NavButton>
          <NavButton to="/alunos">Alunos</NavButton>
          <NavButton to="/comunicados">Comunicados</NavButton>
          {isAdmin && <NavButton to="/admin">Admin</NavButton>}

          <IconButton
            onClick={toggleTheme}
            sx={(t) => ({ color: t.palette.primary.contrastText })}
            aria-label={`Alternar tema (atual: ${mode})`}
          >
            {mode === "light" ? <Brightness4Icon /> : <Brightness7Icon />}
          </IconButton>

          <LogoutButton />
        </Toolbar>
      </AppBar>

      <Container sx={{ py: 3 }}>{children}</Container>
    </>
  );
}
