import { useMemo, useState } from "react";
import {
  Table,
  TableHead,
  TableBody,
  TableRow,
  TableCell,
  IconButton,
  Stack,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  Button,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import VisibilityIcon from "@mui/icons-material/Visibility";
import { useAuth } from "../context/AuthContext";

export default function StudentsTable({ items, onShow, onEdit, onDelete }) {
  const { hasRole } = useAuth();
  const isAdmin = hasRole("ADMIN");

  const [confirmOpen, setConfirmOpen] = useState(false);
  const [selected, setSelected] = useState(null); // { id, nome }
  const [deleting, setDeleting] = useState(false);

  const selectedName = useMemo(() => selected?.nome ?? "este aluno", [selected]);

  const askDelete = (aluno) => {
    setSelected({ id: aluno.id, nome: aluno.nome });
    setConfirmOpen(true);
  };

  const handleCancel = () => {
    if (deleting) return;
    setConfirmOpen(false);
    setSelected(null);
  };

  const handleConfirm = async () => {
    if (!selected?.id) return;

    try {
      setDeleting(true);
      await onDelete(selected.id);
      setConfirmOpen(false);
      setSelected(null);
    } finally {
      setDeleting(false);
    }
  };

  if (!items.length) return <p>Nenhum aluno cadastrado.</p>;

  return (
    <>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Nome</TableCell>
            <TableCell>Faixa</TableCell>
            <TableCell>Telefone</TableCell>
            <TableCell>Email</TableCell>
            <TableCell>Plano</TableCell>
            <TableCell align="right">Ações</TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {items.map((a) => (
            <TableRow key={a.id} hover>
              <TableCell>{a.nome}</TableCell>
              <TableCell>{a.faixa}</TableCell>
              <TableCell>{a.telefone}</TableCell>
              <TableCell>{a.email}</TableCell>
              <TableCell>{a.plano}</TableCell>

              <TableCell align="right">
                <Stack direction="row" spacing={1} justifyContent="flex-end">
                  <IconButton
                    onClick={() => onShow?.(a.id)}
                    aria-label={`Ver detalhes ${a.nome}`}
                    title="Ver detalhes"
                  >
                    <VisibilityIcon />
                  </IconButton>

                  {isAdmin && (
                    <>
                      <IconButton
                        color="primary"
                        onClick={() => onEdit(a.id)}
                        aria-label={`Editar ${a.nome}`}
                        title="Editar"
                      >
                        <EditIcon />
                      </IconButton>

                      <IconButton
                        color="error"
                        onClick={() => askDelete(a)}
                        aria-label={`Excluir ${a.nome}`}
                        title="Excluir"
                      >
                        <DeleteIcon />
                      </IconButton>
                    </>
                  )}
                </Stack>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={confirmOpen} onClose={handleCancel}>
        <DialogTitle>Excluir aluno</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Tem certeza que deseja excluir <b>{selectedName}</b>? Essa ação não pode ser desfeita.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCancel} disabled={deleting}>
            Cancelar
          </Button>
          <Button
            onClick={handleConfirm}
            color="error"
            variant="contained"
            disabled={deleting}
          >
            {deleting ? "Excluindo..." : "Excluir"}
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
