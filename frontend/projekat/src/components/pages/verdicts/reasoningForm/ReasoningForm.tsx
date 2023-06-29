import React, { useState } from "react";


import "./ReasoningForm.css";
import { getReasoningResult } from "../../../../services/requestService";
import VerdictPreview from "../../../elements/VerdictPreview/VerdictPreview";
import LoadingSpinner from "../../../elements/LoadingSpinner/loadingSpinner";

// import MuiAlert from "@mui/material/Alert";

// const Alert = React.forwardRef(function Alert(props:any, ref:any) {
//   return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
// });

function getRandomInt(min: number, max: number): number {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

const initialReasoningForm = () => ({
    okrivljeni: "",
    krivicnoDelo: "Stavljanje u promet opojnih droga (čl. 300. st. 1.)",
    supstanca: "",
    prodaje: "ne",
    proizvodi: "ne",
    poseduje: "ne",
    posreduje: "ne",
    usao_u_zemlju: "ne",
    organizuje_mrezu: "ne",
    prodaje_ugrozenim: "ne",
    is_sluzbeno_lice: "ne",
    drukao: "ne",
    obezbedjuje_materijal: "ne",
    obezbedjuje_supstancu: "ne",
    prisustvo_supstanci: "ne",
});

const initialResidualForm = () => ({
    id: getRandomInt(1000, 50000),
    sud: "",
    optuzeni: "",
    poslovniBroj: "",
    sudija: "",
    tuzilac: "",
    zapisnicar: "",
    kazna: 0,
    primenjeniPropisi: [],
});

export default function ReasoningForm() {
    const [reasoningForm, setReasoningForm] = useState(initialReasoningForm());
  
    const [residualForm, setResidualForm] = useState(initialResidualForm());
  
    const [isResidualFormVisible, setIsResidualFormVisible] = useState(false);
    const [ruleBasedAnswer, setRuleBasedAnswer] = useState("");
    const [caseBasedAnswer, setCaseBasedAnswer] = useState([] as Array<object>);
    const [isLoading, setIsLoading] = useState(false);
  
    const parseCases = (strArr: Array<string>) =>
      strArr.map((strCase: string) =>
        JSON.parse(strCase)
      );
  
    const submitForReasoning = (event: React.FormEvent<HTMLFormElement>) => {
      event.preventDefault()
      setIsLoading(true);
      console.log('OVDE 1')
      getReasoningResult(reasoningForm)
        .then((reasoningResult) => {
          setRuleBasedAnswer(reasoningResult.appliedProvisions);
          reasoningResult.similarCases.forEach((element: any) => {
            console.table(element)
            console.log(JSON.parse(element))
          });
          const cases: Array<object> = parseCases(reasoningResult.similarCases);
          console.table(cases)
          setCaseBasedAnswer(cases);
          setIsResidualFormVisible(true);
          console.log(caseBasedAnswer)
          setIsLoading(false);
        })
        .catch((error) => { console.log(error) });
    };
  
    const submitVerdict = () => {
        console.log("retrieved successfully");
        setIsResidualFormVisible(false);
        setRuleBasedAnswer("");
        setCaseBasedAnswer([]);
        setReasoningForm(() => initialReasoningForm());
        setResidualForm(() => initialResidualForm());
        //setAlert(true);
    };
  
    //const [alert, setAlert] = React.useState(false);
  
    // const handleClose = (event: any, reason: any) => {
    //   if (reason === "clickaway") {
    //     return;
    //   }
  
    //   setAlert(false);
    // };
  
    return (
      <div className="d-flex h-100">
        <div className="d-flex col">
          <div className="w-50 html-preview p-3">
            <div className="create-title-header">Unesite novi slučaj </div>
            <div className="d-flex">
              <div className="w-50 h-100 mx-2">
                <div>
                  <form onSubmit={submitForReasoning}>

                    <label>Pronadjene supstance:</label>

                    <input
                      disabled={isResidualFormVisible}
                      type="text"
                      value={reasoningForm.supstanca}
                      onChange={(event) => {
                        setReasoningForm({
                          ...reasoningForm,
                          supstanca: event.target.value,
                        });
                      }}
                    /> 

                    <br />

                    <label>Prodaje:</label>
  
                    <input type="text"
                      disabled={isResidualFormVisible}
                      value={reasoningForm.prodaje}
                      onChange={(event) => {
                        setReasoningForm({
                          ...reasoningForm,
                          prodaje: event?.target.value,
                        });
                      }}
                    />

                    <br />

                    <label>Proizvodi:</label>

                    <input type="text"
                      disabled={isResidualFormVisible}
                      value={reasoningForm.proizvodi}
                      onChange={(event) => {
                        setReasoningForm({
                          ...reasoningForm,
                          proizvodi: event?.target.value,
                        });
                      }}
                    />

                    <br />


                    <label>Poseduje:</label>

                    <input type="text"
                      disabled={isResidualFormVisible}
                      value={reasoningForm.poseduje}
                      onChange={(event) => {
                        setReasoningForm({
                          ...reasoningForm,
                          poseduje: event?.target.value,
                        });
                      }}
                    />

                    <br />


                    <label>Posreduje:</label>

                    <input type="text"
                      disabled={isResidualFormVisible}
                      value={reasoningForm.posreduje}
                      onChange={(event) => {
                        setReasoningForm({
                          ...reasoningForm,
                          posreduje: event?.target.value,
                        });
                      }}
                    />

                    <br />


                    <label>Usao u zemlju:</label>

                    <input type="text"
                      disabled={isResidualFormVisible}
                      value={reasoningForm.usao_u_zemlju}
                      onChange={(event) => {
                        setReasoningForm({
                          ...reasoningForm,
                          usao_u_zemlju: event?.target.value,
                        });
                      }}
                    />

                    <br />



                    <label>Organizuje OKG:</label>

                    <input type="text"
                      disabled={isResidualFormVisible}
                      value={reasoningForm.organizuje_mrezu}
                      onChange={(event) => {
                        setReasoningForm({
                          ...reasoningForm,
                          organizuje_mrezu: event?.target.value,
                        });
                      }}
                    />

                    <br />


                    <label>Zloupotreba polozaja:</label>

                    <input type="text"
                      disabled={isResidualFormVisible}
                      value={reasoningForm.proizvodi}
                      onChange={(event) => {
                        setReasoningForm({
                          ...reasoningForm,
                          is_sluzbeno_lice: event?.target.value,
                        });
                      }}
                    />
                    <br />
                    



                    <label>Svedok saradnik:</label>

                    <input type="text"
                      disabled={isResidualFormVisible}
                      value={reasoningForm.drukao}
                      onChange={(event) => {
                        setReasoningForm({
                          ...reasoningForm,
                          drukao: event?.target.value,
                        });
                      }}
                    />

                    <br />



                    <label>Obezbedjuje materijal:</label>

                    <input type="text"
                      disabled={isResidualFormVisible}
                      value={reasoningForm.obezbedjuje_materijal}
                      onChange={(event) => {
                        setReasoningForm({
                          ...reasoningForm,
                          obezbedjuje_materijal: event?.target.value,
                        });
                      }}
                    />
                    <br />


                    <label>Obezbedjuje supstancu:</label>

                    <input type="text"
                      disabled={isResidualFormVisible}
                      value={reasoningForm.obezbedjuje_supstancu}
                      onChange={(event) => {
                        setReasoningForm({
                          ...reasoningForm,
                          obezbedjuje_supstancu: event?.target.value,
                        });
                      }}
                    />
                    <br />


                    <label>Prisustvo supstanci:</label>

                    <input type="text"
                      disabled={isResidualFormVisible}
                      value={reasoningForm.prisustvo_supstanci}
                      onChange={(event) => {
                        setReasoningForm({
                          ...reasoningForm,
                          prisustvo_supstanci: event?.target.value,
                        });
                      }}
                    />
                    <br />

  
                    <div className="mt-3">
                      <input type="submit"
                        disabled={isResidualFormVisible}      
                        value="Rezonuj"                
                      />
                    </div>
                  </form>
                </div>
              </div>
              <div className="w-50 h-100 mx-2">
                {isResidualFormVisible && (
                  <form>
                    <input type="text"
                      placeholder="Sud"
                      value={residualForm.sud}
                      onChange={(event) => {
                        setResidualForm({
                          ...residualForm,
                          sud: event.target.value,
                        });
                      }}
                    />
  
                    <input type="text"
                      placeholder="Poslovni broj"
                      value={residualForm.poslovniBroj}
                      onChange={(event) => {
                        setResidualForm({
                          ...residualForm,
                          poslovniBroj: event.target.value,
                        });
                      }}
                    />

                    <input type="text"
                      placeholder="Optuzeni"
                      value={residualForm.optuzeni}
                      onChange={(event) => {
                        setResidualForm({
                          ...residualForm,
                          optuzeni: event.target.value,
                        });
                      }}
                    />
  
                    <input type="text"
                      placeholder="Sudija"
                      value={residualForm.sudija}
                      onChange={(event) => {
                        setResidualForm({
                          ...residualForm,
                          sudija: event.target.value,
                        });
                      }}
                    />
  
                    <input type="text"
                      placeholder="Zapisnicar"
                      value={residualForm.zapisnicar}
                      onChange={(event) => {
                        setResidualForm({
                          ...residualForm,
                          zapisnicar: event.target.value,
                        });
                      }}
                    />
  
                    <div>
                      <button
                        
                        onClick={submitVerdict}
                        
                      >
                        Dodaj novu presudu
                      </button>
                    </div>
                  </form>
                )}
              </div>
            </div>
          </div>
  
          <div className="w-50 html-preview">
            <div className="h-25 p-3 border-bottom">
              <div className="create-title-header">
                Rezultati rasuđivanja po pravilima
              </div>
              {isLoading && <LoadingSpinner />}
              {ruleBasedAnswer && !isLoading && <div>{ruleBasedAnswer}</div>}
            </div>
            <div className="h-75 p-3">
              <div className="create-title-header">
                Rezultati rasuđivanja po slučajevima
              </div>
              {isLoading && <LoadingSpinner />}
              {caseBasedAnswer && !isLoading && (
                <>
                  <small className="my-3">
                    prikazano je 5 najsličnijih slučajeva poređanih od
                    najsličnijeg ka manje sličnima
                  </small>
                  <div className="similar-jdg-container overflow-auto">
                    {caseBasedAnswer.map((similarCase: CaseDescription) => (
                      <VerdictPreview
                        key={similarCase.poslovniBroj!}
                        verdict={similarCase}
                      />
                    ))}
                  </div>
                </>
              )}
            </div>
          </div>
        </div>
        {/* <Snackbar open={alert} autoHideDuration={6000} onClose={handleClose}>
          <Alert onClose={handleClose} severity="success" sx={{ width: "100%" }}>
            Nova presuda uspešno snimljena.
          </Alert>
        </Snackbar> */}
      </div>
    );
  }

