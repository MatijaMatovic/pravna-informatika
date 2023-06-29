import getFetchProxy from "./apiService";

const proxy = getFetchProxy();

export const getVerdictNames = () =>
  proxy.executeRequest({ path: `documents/verdicts` });

export const getVerdictXML = (verdictID: string) =>
  proxy.fetchXML({ path: `documents/verdict/xml/${verdictID}` });

export const getLawXML = () => proxy.fetchXML({ path: `documents/law/xml` });

export const getVerdictFeatures = () =>
  proxy
    .fetchXML({ path: `documents/features` })
    .then((features) => JSON.parse(features) as CaseDescription);

export const getReasoningResult = (data: object) => {
  console.log('getReasoningResult requestService');
  let responsePromise = proxy.executeRequest({ path: `start_reasoning`, method: "POST", data })
  console.log(responsePromise)
  return responsePromise
};